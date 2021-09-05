package org.zxp.esclientrhl.repository;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.elasticsearch.search.suggest.phrase.DirectCandidateGeneratorBuilder;
import org.elasticsearch.search.suggest.phrase.PhraseSuggestion;
import org.elasticsearch.search.suggest.phrase.PhraseSuggestionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.zxp.esclientrhl.annotation.ESID;
import org.zxp.esclientrhl.annotation.ESMapping;
import org.zxp.esclientrhl.config.ElasticsearchProperties;
import org.zxp.esclientrhl.enums.DataType;
import org.zxp.esclientrhl.enums.SqlFormat;
import org.zxp.esclientrhl.index.ElasticsearchIndex;
import org.zxp.esclientrhl.repository.response.ScrollResponse;
import org.zxp.esclientrhl.repository.response.UriResponse;
import org.zxp.esclientrhl.util.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * program: esdemo
 * description: Elasticsearch基础功能组件实现类
 * author: X-Pacific zhang
 * create: 2019-01-18 16:04
 **/
@Component
public class ElasticsearchTemplateImpl<T, M> implements ElasticsearchTemplate<T, M> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestHighLevelClient client;

    @Autowired
    ElasticsearchIndex elasticsearchIndex;


    @Override
    public Response request(Request request) throws Exception {
        Response response = client.getLowLevelClient().performRequest(request);
        return response;
    }

    @Override
    public boolean save(T t) throws Exception {
        return save(t,null);
    }

    @Override
    public boolean save(T t, String routing) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(t.getClass());
        String indexname = metaData.getIndexname();
        String indextype = metaData.getIndextype();
        String id = Tools.getESId(t);
        IndexRequest indexRequest = null;
        if (StringUtils.isEmpty(id)) {
            indexRequest = new IndexRequest(indexname, indextype);
        } else {
            indexRequest = new IndexRequest(indexname, indextype, id);
        }
        String source = JsonUtils.obj2String(t);
        indexRequest.source(source, XContentType.JSON);
        if(!StringUtils.isEmpty(routing)){
            indexRequest.routing(routing);
        }
        IndexResponse indexResponse = null;
        indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            logger.info("INDEX CREATE SUCCESS");
            //异步执行rollover
            elasticsearchIndex.rollover(t.getClass(),true);
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            logger.info("INDEX UPDATE SUCCESS");
        } else {
            return false;
        }
        return true;
    }

    @Override
    public BulkResponse save(List<T> list) throws Exception {
        if (list == null || list.size() == 0) {
            return null;
        }
        T t = list.get(0);
        MetaData metaData = elasticsearchIndex.getMetaData(t.getClass());
        String indexname = metaData.getIndexname();
        String indextype = metaData.getIndextype();
        return savePart(list,indexname,indextype);
    }

    @Override
    public BulkResponse[] saveBatch(List<T> list) throws Exception {
        if (list == null || list.size() == 0) {
            return null;
        }
        T t = list.get(0);
        MetaData metaData = elasticsearchIndex.getMetaData(t.getClass());
        String indexname = metaData.getIndexname();
        String indextype = metaData.getIndextype();
        List<List<T>> lists = Tools.splitList(list, true);
        BulkResponse[] bulkResponses = new BulkResponse[lists.size()];
        for (int i = 0; i < lists.size(); i++) {
            bulkResponses[i] = savePart(lists.get(i),indexname,indextype);
        }
        return bulkResponses;
    }

    private BulkResponse savePart(List<T> list,String indexname,String indextype) throws Exception {
        BulkRequest rrr = new BulkRequest();
        Class clazz = null;
        for (int i = 0; i < list.size(); i++) {
            T tt = list.get(i);
            clazz = tt.getClass();
            String id = Tools.getESId(tt);
            String sourceJsonStr = JsonUtils.obj2String(tt);
            rrr.add(new IndexRequest(indexname, indextype, id)
//                    .source(BeanTools.objectToMap(tt)));
                    .source(sourceJsonStr, XContentType.JSON));
        }
        BulkResponse bulkResponse = client.bulk(rrr, RequestOptions.DEFAULT);
        //异步执行rollover
        elasticsearchIndex.rollover(clazz,true);
        return bulkResponse;
    }

    @Override
    public BulkResponse bulkUpdate(List<T> list) throws Exception {
        if (list == null || list.size() == 0) {
            return null;
        }
        T t = list.get(0);
        if(Tools.checkNested(t)){
            throw new Exception("nested对象更新，请使用覆盖更新");
        }
        MetaData metaData = elasticsearchIndex.getMetaData(t.getClass());
        String indexname = metaData.getIndexname();
        String indextype = metaData.getIndextype();
        return updatePart(list, indexname, indextype);
    }

    @Override
    public BulkResponse[] bulkUpdateBatch(List<T> list) throws Exception {
        if (list == null || list.size() == 0) {
            return null;
        }
        T t = list.get(0);
        if(Tools.checkNested(t)){
            throw new Exception("nested对象更新，请使用覆盖更新");
        }
        MetaData metaData = elasticsearchIndex.getMetaData(t.getClass());
        String indexname = metaData.getIndexname();
        String indextype = metaData.getIndextype();
        List<List<T>> lists = Tools.splitList(list, true);
        BulkResponse[] bulkResponses = new BulkResponse[lists.size()];
        for (int i = 0; i < lists.size(); i++) {
            bulkResponses[i] = updatePart(lists.get(i),indexname,indextype);
        }
        return bulkResponses;
    }

    private BulkResponse updatePart(List<T> list,String indexname,String indextype) throws Exception {
        BulkRequest rrr = new BulkRequest();
        for (int i = 0; i < list.size(); i++) {
            T tt = list.get(i);
            String id = Tools.getESId(tt);
            rrr.add(new UpdateRequest(indexname, indextype, id)
                    .doc(Tools.getFieldValue(tt)));
        }
        BulkResponse bulkResponse = client.bulk(rrr, RequestOptions.DEFAULT);
        return bulkResponse;
    }

    @Override
    public boolean update(T t) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(t.getClass());
        String indexname = metaData.getIndexname();
        String indextype = metaData.getIndextype();
        String id = Tools.getESId(t);
        if (StringUtils.isEmpty(id)) {
            throw new Exception("ID cannot be empty");
        }
        if(Tools.checkNested(t)){
            throw new Exception("nested对象更新，请使用覆盖更新");
        }
        UpdateRequest updateRequest = new UpdateRequest(indexname, indextype, id);
        updateRequest.doc(Tools.getFieldValue(t));
        UpdateResponse updateResponse = null;
        updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
            logger.info("INDEX CREATE SUCCESS");
        } else if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            logger.info("INDEX UPDATE SUCCESS");
        } else {
            return false;
        }
        return true;
    }

    @Override
    public BulkResponse batchUpdate(QueryBuilder queryBuilder, T t, Class clazz, int limitcount, boolean asyn) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(t.getClass());
        String indexname = metaData.getIndexname();
        String indextype = metaData.getIndextype();
        if (queryBuilder == null) {
            throw new NullPointerException();
        }
        if(Tools.checkNested(t)){
            throw new Exception("nested对象更新，请使用覆盖更新");
        }
        if(Tools.getESId(t) == null || "".equals(Tools.getESId(t))) {
            PageSortHighLight psh = new PageSortHighLight(1, limitcount);
            psh.setHighLight(null);
            PageList pageList = this.search(queryBuilder, psh, clazz, indexname);
            if (pageList.getTotalElements() > limitcount) {
                throw new Exception("beyond the limitcount");
            }
            if (asyn) {
                new Thread(() -> {
                    try {
                        batchUpdate(pageList.getList(), indexname, indextype, t);
                        logger.info("asyn batch finished update");
                    } catch (Exception e) {
                        logger.error("asyn batch update fail", e);
                    }
                }).start();
                return null;
            } else {
                return batchUpdate(pageList.getList(), indexname, indextype, t);
            }
        }else{
            throw new Exception("批量更新请不要给主键传值");
        }
    }

    private BulkResponse batchUpdate(List<T> list, String indexname, String indextype,T tot) throws Exception {
        Map map = Tools.getFieldValue(tot);
        BulkRequest rrr = new BulkRequest();
        for (int i = 0; i < list.size(); i++) {
            T tt = list.get(i);
            rrr.add(new UpdateRequest(indexname, indextype, Tools.getESId(tt))
                    .doc(map));
        }
        BulkResponse bulkResponse = client.bulk(rrr, RequestOptions.DEFAULT);
        return bulkResponse;
    }


    @Override
    public boolean updateCover(T t) throws Exception {
        return save(t);
    }

    @Override
    public boolean delete(T t) throws Exception {
        return delete(t,null);
    }

    @Override
    public boolean delete(T t, String routing) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(t.getClass());
        String indexname = metaData.getIndexname();
        String indextype = metaData.getIndextype();
        String id = Tools.getESId(t);
        if (StringUtils.isEmpty(id)) {
            throw new Exception("ID cannot be empty");
        }
        DeleteRequest deleteRequest = new DeleteRequest(indexname, indextype, id);
        if(!StringUtils.isEmpty(routing)){
            deleteRequest.routing(routing);
        }
        DeleteResponse deleteResponse = null;
        deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED) {
            logger.info("INDEX DELETE SUCCESS");
        } else {
            return false;
        }
        return true;
    }

    @Override
    public BulkByScrollResponse deleteByCondition(QueryBuilder queryBuilder, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String[] indexname = metaData.getSearchIndexNames();
        DeleteByQueryRequest request = new DeleteByQueryRequest(indexname);
        request.setQuery(queryBuilder);
        BulkByScrollResponse bulkResponse = client.deleteByQuery(request, RequestOptions.DEFAULT);
        return bulkResponse;
    }


    @Override
    public SearchResponse search(SearchRequest searchRequest) throws IOException {
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse;
    }

    @Override
    public List<T> search(QueryBuilder queryBuilder, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String[] indexname = metaData.getSearchIndexNames();
        return search(queryBuilder, clazz, indexname);
    }

    @Override
    public List<T> search(QueryBuilder queryBuilder, Class<T> clazz, String... indexs) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String indextype = metaData.getIndextype();
        List<T> list = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest(indexs);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(Constant.DEFALT_PAGE_SIZE);
        searchRequest.source(searchSourceBuilder);
        if (metaData.isPrintLog()) {
            logger.info(searchSourceBuilder.toString());
        }
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            T t = JsonUtils.string2Obj(hit.getSourceAsString(), clazz);
            //将_id字段重新赋值给@ESID注解的字段
            correctID(clazz, t, (M)hit.getId());
            list.add(t);
        }
        return list;
    }

    @Override
    public List<T> searchMore(QueryBuilder queryBuilder,int limitSize, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String[] indexname = metaData.getSearchIndexNames();
        return searchMore(queryBuilder,limitSize,clazz,indexname);
    }

    @Override
    public List<T> searchMore(QueryBuilder queryBuilder,int limitSize, Class<T> clazz, String... indexs) throws Exception {
        PageSortHighLight pageSortHighLight = new PageSortHighLight(1, limitSize);
        PageList pageList = search(queryBuilder, pageSortHighLight, clazz, indexs);
        if(pageList != null){
            return pageList.getList();
        }
        return null;
    }

    @Override
    public List<T> searchUri(String uri, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String indexname = metaData.getIndexname();
        String indextype = metaData.getIndextype();
        List<T> list = new ArrayList<>();
        Request request = new Request("GET","/"+indexname+"/"+indextype+"/_search/?"+uri);
        Response response = request(request);
        String responseBody = EntityUtils.toString(response.getEntity());
        if (metaData.isPrintLog()) {
            logger.info("searchUri请求报文："+"/"+indexname+"/"+indextype+"/_search/?"+uri);
            logger.info("searchUri返回报文："+responseBody);
        }
        UriResponse uriResponse = JsonUtils.string2Obj(responseBody, UriResponse.class);

        T[] ts = (T[]) Array.newInstance(clazz, uriResponse.getHits().getHits().size());
        for (int i = 0; i < uriResponse.getHits().getHits().size(); i++) {
            T t = (T) clazz.newInstance();
            //先将LinkedHashMap（json解析后是Map类型）转化成Object
            Object obj = BeanTools.mapToObject((Map) uriResponse.getHits().getHits().get(i).get_source(),clazz);
            //将Object属性拷贝
            BeanUtils.copyProperties(obj, t);
            //将_id字段重新赋值给@ESID注解的字段
            correctID(clazz, t, (M)uriResponse.getHits().getHits().get(i).get_id());
            ts[i] = t;
        }
        return Arrays.asList(ts);
    }

    @Autowired
    ElasticsearchProperties elasticsearchProperties;

    @Override
    public String queryBySQL(String sql, SqlFormat sqlFormat) throws Exception {
        String host = elasticsearchProperties.getHost();
        if(StringUtils.isEmpty(host)){
            host = Constant.DEFAULT_ES_HOST;
        }
        String ipport = "";
        String[] hosts = host.split(",");
        if(hosts.length == 1){
            ipport = hosts[0];
        }else{//随机选择配置的地址
            int randomindex = new Random().nextInt(hosts.length);
            ipport = hosts[randomindex];
        }
        ipport = "http://"+ipport;
        logger.info(ipport+"/_sql?format="+sqlFormat.getFormat());
        logger.info("{\"query\":\""+sql+"\"}");

        String username = elasticsearchProperties.getUsername();
        String password = elasticsearchProperties.getPassword();
        if(!StringUtils.isEmpty(username)) {
            return HttpClientTool.execute(ipport+"/_sql?format="+sqlFormat.getFormat(),"{\"query\":\""+sql+"\"}",username,password);
        }
        return HttpClientTool.execute(ipport+"/_sql?format="+sqlFormat.getFormat(),"{\"query\":\""+sql+"\"}");
    }


    @Override
    public long count(QueryBuilder queryBuilder, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String[] indexname = metaData.getSearchIndexNames();
        return count(queryBuilder, clazz, indexname);
    }

    @Override
    public long count(QueryBuilder queryBuilder, Class<T> clazz, String... indexs) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        CountRequest countRequest = new CountRequest(indexs);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        countRequest.source(searchSourceBuilder);
        CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
        long count = countResponse.getCount();
        return count;
    }

    @Override
    public T getById(M id, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String indexname = metaData.getIndexname();
        String indextype = metaData.getIndextype();
        if (StringUtils.isEmpty(id)) {
            throw new Exception("ID cannot be empty");
        }
        GetRequest getRequest = new GetRequest(indexname, indextype, id.toString());
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        if (getResponse.isExists()) {
            return JsonUtils.string2Obj(getResponse.getSourceAsString(), clazz);
        }
        return null;
    }

    @Override
    public List<T> mgetById(M[] ids, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String indexname = metaData.getIndexname();
        String indextype = metaData.getIndextype();
        MultiGetRequest request = new MultiGetRequest();
        for (int i = 0; i < ids.length; i++) {
            request.add(new MultiGetRequest.Item(indexname, indextype, ids[i].toString()));
        }
        MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
        List<T> list = new ArrayList<>();
        for (int i = 0; i < response.getResponses().length; i++) {
            MultiGetItemResponse item = response.getResponses()[i];
            GetResponse getResponse = item.getResponse();
            if (getResponse.isExists()) {
                list.add(JsonUtils.string2Obj(getResponse.getSourceAsString(), clazz));
            }
        }
        return list;
    }

    @Override
    public boolean exists(M id, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String indexname = metaData.getIndexname();
        String indextype = metaData.getIndextype();
        if (StringUtils.isEmpty(id)) {
            throw new Exception("ID cannot be empty");
        }
        GetRequest getRequest = new GetRequest(indexname, indextype, id.toString());
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        if (getResponse.isExists()) {
            return true;
        }
        return false;
    }

    private static final String keyword = ".keyword";

    /**
     * 组织字段是否带有.keyword（对于当前es版本默认不打开field_data）
     *  1、如果入参字段名称带有.keyword不处理
     *  2、如果是非分词类型的字段（非text）不处理
     *  3、如果是分词类型的字段（text类型）且配置了ESMapping且包含kerword子字段或者没有配置ESMapping，则拼接keyword子字段
     *  4、如果是分词类型的字段（text类型）且配置了ESMapping且不包含kerword子字段，会自动报错
     *  doc values
     *      建立索引时会默认建立正排索引和倒排索引两种
     *      就是正排索引 排序，聚合，过滤使用
     *      doc values是被保存在磁盘上的，此时如果内存足够，os会自动将其缓存在内存中，性能还是会很高；如果内存不足够，os会将其写入磁盘上
     *      分词field，是没有doc values的（强行打开会报错）
     * field_data
     *      如果想对分词字段进行排序，聚合，需要将对应字段的field_data打开（词频统计）
     *      Fielddata和doc values结构是类似的
     *      默认是关闭的
     *      保存于heap内存中
     * 如果想统计词频，需要手工设定索引并打开field_data，将ESMapping中的keyword关闭
     * @param field
     * @param name
     * @return
     */
    private String genKeyword(Field field, String name) {
        ESMapping esMapping = field.getAnnotation(ESMapping.class);
        //带着.keyword直接忽略
        if (name == null || name.indexOf(keyword) > -1) {
            return name;
        }
        //只要keyword是true就要拼接
        //没配注解，但是类型是字符串，默认keyword是true
        if (esMapping == null) {
            if (field.getType() == String.class) {
                return name + keyword;
            }
        }
        //配了注解，但是类型是字符串，默认keyword是true
        else {
            if (esMapping.datatype() == DataType.text_type && esMapping.keyword() == true) {
                return name + keyword;
            }
        }
        return name;
    }

    @Override
    public Aggregations aggs(AggregationBuilder aggregationBuilder, QueryBuilder queryBuilder, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String[] indexname = metaData.getSearchIndexNames();
        return aggs(aggregationBuilder, queryBuilder, clazz, indexname);
    }

    @Override
    public Aggregations aggs(AggregationBuilder aggregationBuilder, QueryBuilder queryBuilder, Class<T> clazz, String... indexs) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String[] indexname = indexs;
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if (queryBuilder != null) {
            searchSourceBuilder.query(queryBuilder);
        }
        searchSourceBuilder.size(0);
        searchSourceBuilder.aggregation(aggregationBuilder);
        SearchRequest searchRequest = new SearchRequest(indexname);
        searchRequest.source(searchSourceBuilder);
        if (metaData.isPrintLog()) {
            logger.info(searchSourceBuilder.toString());
        }
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return searchResponse.getAggregations();
    }

    @Override
    public boolean deleteById(M id, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String indexname = metaData.getIndexname();
        String indextype = metaData.getIndextype();
        if (StringUtils.isEmpty(id)) {
            throw new Exception("ID cannot be empty");
        }
        DeleteRequest deleteRequest = new DeleteRequest(indexname, indextype, id.toString());
        DeleteResponse deleteResponse = null;
        deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED) {
            logger.info("INDEX DELETE SUCCESS");
        } else {
            return false;
        }
        return true;
    }

    @Override
    public PageList<T> search(QueryBuilder queryBuilder, PageSortHighLight pageSortHighLight, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String[] indexname = metaData.getSearchIndexNames();
        if (pageSortHighLight == null) {
            throw new NullPointerException("PageSortHighLight不能为空!");
        }
        return search(queryBuilder, pageSortHighLight, clazz, indexname);
    }

    @Override
    public PageList<T> search(QueryBuilder queryBuilder, PageSortHighLight pageSortHighLight, Class<T> clazz, String... indexs) throws Exception {
        if (pageSortHighLight == null) {
            throw new NullPointerException("PageSortHighLight不能为空!");
        }
        Attach attach = new Attach();
        attach.setPageSortHighLight(pageSortHighLight);
        return search(queryBuilder,attach,clazz,indexs);
    }

    @Override
    public PageList<T> search(QueryBuilder queryBuilder, Attach attach, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String[] indexname = metaData.getSearchIndexNames();
        if (attach == null) {
            throw new NullPointerException("Attach不能为空!");
        }
        return search(queryBuilder, attach, clazz, indexname);
    }

    @Override
    public PageList<T> search(QueryBuilder queryBuilder, Attach attach, Class<T> clazz, String... indexs) throws Exception {
        if (attach == null) {
            throw new NullPointerException("Attach不能为空!");
        }
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        PageList<T> pageList = new PageList<>();
        List<T> list = new ArrayList<>();
        PageSortHighLight pageSortHighLight = attach.getPageSortHighLight();
        SearchRequest searchRequest = new SearchRequest(indexs);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        boolean highLightFlag = false;
        boolean idSortFlag= false;
        if(pageSortHighLight != null) {
            //设置当前页码
            pageList.setCurrentPage(pageSortHighLight.getCurrentPage());
            pageList.setPageSize(pageSortHighLight.getPageSize());
            //分页
            if (pageSortHighLight.getPageSize() != 0) {
                //search after不可指定from
                if(!attach.isSearchAfter()) {
                    searchSourceBuilder.from((pageSortHighLight.getCurrentPage() - 1) * pageSortHighLight.getPageSize());
                }
                searchSourceBuilder.size(pageSortHighLight.getPageSize());
            }
            //排序
            if (pageSortHighLight.getSort() != null) {
                Sort sort = pageSortHighLight.getSort();
                List<Sort.Order> orders = sort.listOrders();
                for (int i = 0; i < orders.size(); i++) {
                    if(orders.get(i).getProperty().equals("_id")){
                        idSortFlag = true;
                    }
                    searchSourceBuilder.sort(new FieldSortBuilder(orders.get(i).getProperty()).order(orders.get(i).getDirection()));
                }
            }
            //高亮
            //https://www.elastic.co/guide/en/elasticsearch/reference/7.12/highlighting.html
            //https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.12/java-rest-high-search.html#java-rest-high-search-request-highlighting
            HighLight highLight = pageSortHighLight.getHighLight();
            if(highLight != null && highLight.getHighlightBuilder() != null){
                searchSourceBuilder.highlighter(highLight.getHighlightBuilder());
            }
            else if (highLight != null && highLight.getHighLightList() != null && highLight.getHighLightList().size() != 0) {
                HighlightBuilder highlightBuilder = new HighlightBuilder();
                if (!StringUtils.isEmpty(highLight.getPreTag()) && !StringUtils.isEmpty(highLight.getPostTag())) {
                    highlightBuilder.preTags(highLight.getPreTag());
                    highlightBuilder.postTags(highLight.getPostTag());
                }
                for (int i = 0; i < highLight.getHighLightList().size(); i++) {
                    highLightFlag = true;
                    // You can set fragment_size to 0 to never split any sentence.
                    //不对高亮结果进行拆分
                    highlightBuilder.field(highLight.getHighLightList().get(i), 0);
                }
                searchSourceBuilder.highlighter(highlightBuilder);
            }
        }
        //设定searchAfter
        if(attach.isSearchAfter()){
            if(pageSortHighLight == null || pageSortHighLight.getPageSize() == 0){
                searchSourceBuilder.size(10);
            }else{
                searchSourceBuilder.size(pageSortHighLight.getPageSize());
            }
            if(attach.getSortValues() != null && attach.getSortValues().length != 0) {
                searchSourceBuilder.searchAfter(attach.getSortValues());
            }
            //如果没拼_id的排序，自动添加保证排序唯一性
            if(!idSortFlag){
                Sort.Order order = new Sort.Order(SortOrder.ASC,"_id");
                pageSortHighLight.getSort().and(new Sort(order));
                searchSourceBuilder.sort(new FieldSortBuilder("_id").order(SortOrder.ASC));
            }
        }
        //TrackTotalHits设置为true，解除查询结果超出10000的限制
        if(attach.isTrackTotalHits()){
            searchSourceBuilder.trackTotalHits(attach.isTrackTotalHits());
        }

        //设定返回source
        if(attach.getExcludes()!= null || attach.getIncludes() != null){
            searchSourceBuilder.fetchSource(attach.getIncludes(),attach.getExcludes());
        }
        searchRequest.source(searchSourceBuilder);
        //设定routing
        if(!StringUtils.isEmpty(attach.getRouting())){
            searchRequest.routing(attach.getRouting());
        }
        if (metaData.isPrintLog()) {
            logger.info(searchSourceBuilder.toString());
        }
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            T t = JsonUtils.string2Obj(hit.getSourceAsString(), clazz);
            //将_id字段重新赋值给@ESID注解的字段
            correctID(clazz, t, (M)hit.getId());
            //替换高亮字段
            if (highLightFlag) {
                Map<String, HighlightField> hmap = hit.getHighlightFields();
                hmap.forEach((k, v) ->
                        {
                            try {
                                Object obj = mapToObject(hmap, clazz);
                                BeanUtils.copyProperties(obj, t, BeanTools.getNoValuePropertyNames(obj));
                            } catch (Exception e) {
                                logger.error("convert object error", e);
                            }
                        }
                );
            }
            list.add(t);
            //最后一条SearchAfter用于searchAfter
            pageList.setSortValues(hit.getSortValues());
        }

        pageList.setList(list);
//        pageList.setTotalElements(hits.getTotalHits());
        pageList.setTotalElements(hits.getTotalHits().value);
        if(pageSortHighLight != null && pageSortHighLight.getPageSize() != 0) {
            pageList.setTotalPages(getTotalPages(hits.getTotalHits().value, pageSortHighLight.getPageSize()));
        }
        return pageList;
    }

    private static Map<Class,String> classIDMap = new ConcurrentHashMap();

    /**
     * 将_id字段重新赋值给@ESID注解的字段
     * @param clazz
     * @param t
     * @param _id
     */
    private void correctID(Class clazz, T t, M _id){
        try{
            if(StringUtils.isEmpty(_id)){
                return;
            }
            if(classIDMap.containsKey(clazz)){
                Field field = clazz.getDeclaredField(classIDMap.get(clazz));
                field.setAccessible(true);
                //这里不支持非String类型的赋值，如果用默认的id，则id的类型一定是String类型的
                if(field.get(t) == null) {
                    field.set(t, _id);
                }
                return;
            }
            for (int i = 0; i < clazz.getDeclaredFields().length; i++) {
                Field field = clazz.getDeclaredFields()[i];
                field.setAccessible(true);
                if(field.getAnnotation(ESID.class) != null){
                    classIDMap.put(clazz,field.getName());
                    //这里不支持非String类型的赋值，如果用默认的id，则id的类型一定是String类型的
                    if(field.get(t) == null) {
                        field.set(t, _id);
                    }
                }
            }
        }catch (Exception e){
            logger.error("correctID error!",e);
        }
    }

    private Object mapToObject(Map map, Class<?> beanClass) throws Exception {
        if (map == null) {
            return null;
        }
        Object obj = beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (map.get(field.getName()) != null && !StringUtils.isEmpty(map.get(field.getName()))) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                if (map.get(field.getName()) instanceof HighlightField && ((HighlightField) map.get(field.getName())).fragments().length > 0) {
                    field.set(obj, ((HighlightField) map.get(field.getName())).fragments()[0].string());
                }
            }
        }
        return obj;
    }

    @Override
    public List<T> searchTemplate(Map<String, Object> template_params, String templateName, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String[] indexname = metaData.getSearchIndexNames();
        SearchTemplateRequest request = new SearchTemplateRequest();
        request.setRequest(new SearchRequest(indexname));

        request.setScriptType(ScriptType.STORED);
        request.setScript(templateName);
        Map<String, Object> params = new HashMap<>();
        if(template_params != null){
            template_params.forEach((k,v) -> {
                params.put(k, v);
            });
        }
        request.setScriptParams(params);
        SearchTemplateResponse response = client.searchTemplate(request, RequestOptions.DEFAULT);
        SearchResponse searchResponse = response.getResponse();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        List<T> list = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            T t = JsonUtils.string2Obj(hit.getSourceAsString(), clazz);
            list.add(t);
        }
        return list;
    }

    @Override
    public List<T> searchTemplateBySource(Map<String, Object> template_params, String templateSource, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String indexname = metaData.getIndexname();
        SearchTemplateRequest request = new SearchTemplateRequest();
        request.setRequest(new SearchRequest(indexname));
        request.setScriptType(ScriptType.INLINE);
        request.setScript(templateSource);
        Map<String, Object> scriptParams = new HashMap<>();
        if(template_params != null){
            template_params.forEach((k,v) -> {
                scriptParams.put(k, v);
            });
        }
        request.setScriptParams(scriptParams);
        SearchTemplateResponse response = client.searchTemplate(request, RequestOptions.DEFAULT);
        SearchResponse searchResponse = response.getResponse();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        List<T> list = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            T t = JsonUtils.string2Obj(hit.getSourceAsString(), clazz);
            list.add(t);
        }
        return list;
    }

    @Override
    public Response saveTemplate(String templateName, String templateSource) throws Exception {
        Request scriptRequest = new Request("POST", "_scripts/"+templateName);
        scriptRequest.setJsonEntity(templateSource);
        Response scriptResponse = request(scriptRequest);
        return scriptResponse;
    }

    @Override
    public List<T> scroll(QueryBuilder queryBuilder, Class<T> clazz, Long time, String... indexs) throws Exception {
        if (queryBuilder == null) {
            queryBuilder = new MatchAllQueryBuilder();
        }
        List<T> list = new ArrayList<>();
        ScrollResponse<T> scrollResponse = createScroll(queryBuilder, clazz, time, 50);
        scrollResponse.getList().forEach(s -> list.add(s));
        String scrollId = scrollResponse.getScrollId();
        while (true){
            scrollResponse = queryScroll(clazz, time, scrollId);
            if(scrollResponse.getList() != null && scrollResponse.getList().size() != 0){
                scrollResponse.getList().forEach(s -> list.add(s));
                scrollId = scrollResponse.getScrollId();
            }else{
                break;
            }
        }
        return list;
    }

    @Override
    public ScrollResponse<T> createScroll(QueryBuilder queryBuilder, Class<T> clazz, Long time, Integer size) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String indexname = metaData.getIndexname();
        return createScroll(queryBuilder,clazz,time,size,indexname);
    }

    @Override
    public ScrollResponse<T> createScroll(QueryBuilder queryBuilder, Class<T> clazz, Long time, Integer size, String... indexs) throws Exception {
        if (queryBuilder == null) {
            queryBuilder = new MatchAllQueryBuilder();
        }
        String[] indexname = indexs;
        List<T> list = new ArrayList<>();
        Scroll scroll = new Scroll(TimeValue.timeValueHours(time));
        SearchRequest searchRequest = new SearchRequest(indexname);
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(size == null || size == 0){
            searchSourceBuilder.size(Constant.DEFAULT_SCROLL_PERPAGE);
        }else{
            searchSourceBuilder.size(size);
        }
        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        //第一把查询出的结果
        for (SearchHit hit : searchHits) {
            T t = JsonUtils.string2Obj(hit.getSourceAsString(), clazz);
            //将_id字段重新赋值给@ESID注解的字段
            correctID(clazz, t, (M)hit.getId());
            list.add(t);
        }
        ScrollResponse<T> scrollResponse = new ScrollResponse(list,scrollId);
        return scrollResponse;
    }

    @Override
    public ScrollResponse<T> queryScroll(Class<T> clazz, Long time , String scrollId) throws Exception {
        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        Scroll scroll = new Scroll(TimeValue.timeValueHours(time));
        scrollRequest.scroll(scroll);
        SearchResponse searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
        scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        List<T> list = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            T t = JsonUtils.string2Obj(hit.getSourceAsString(), clazz);
            //将_id字段重新赋值给@ESID注解的字段
            correctID(clazz, t, (M)hit.getId());
            list.add(t);
        }
        ScrollResponse<T> scrollResponse = new ScrollResponse(list,scrollId);
        return scrollResponse;
    }

    @Override
    public ClearScrollResponse clearScroll(String... scrollId) throws Exception {
        ClearScrollRequest request = new ClearScrollRequest();
        request.setScrollIds(Arrays.asList(scrollId));
        ClearScrollResponse response = client.clearScroll(request, RequestOptions.DEFAULT);
        return response;
    }


    @Override
    public List<T> scroll(QueryBuilder queryBuilder, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String[] indexname = metaData.getSearchIndexNames();
        return scroll(queryBuilder, clazz, Constant.DEFAULT_SCROLL_TIME, indexname);
    }

    @Override
    public List<String> completionSuggest(String fieldName, String fieldValue, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String[] indexname = metaData.getSearchIndexNames();
        return completionSuggest(fieldName, fieldValue, clazz, indexname);
    }

    @Override
    public List<String> completionSuggest(String fieldName, String fieldValue, Class<T> clazz, String... indexs) throws Exception {
        String[] indexname = indexs;
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        CompletionSuggestionBuilder completionSuggestionBuilder = new
                CompletionSuggestionBuilder(fieldName + ".suggest");
        completionSuggestionBuilder.text(fieldValue);
        completionSuggestionBuilder.skipDuplicates(true);
        completionSuggestionBuilder.size(Constant.COMPLETION_SUGGESTION_SIZE);
        suggestBuilder.addSuggestion("suggest_" + fieldName, completionSuggestionBuilder);
        searchSourceBuilder.suggest(suggestBuilder);
        SearchRequest searchRequest = new SearchRequest(indexname);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        Suggest suggest = searchResponse.getSuggest();
        if (suggest == null) {
            return null;
        }
        CompletionSuggestion completionSuggestion = suggest.getSuggestion("suggest_" + fieldName);
        List<String> list = new ArrayList<>();
        for (CompletionSuggestion.Entry entry : completionSuggestion.getEntries()) {
            for (CompletionSuggestion.Entry.Option option : entry) {
                String suggestText = option.getText().string();
                list.add(suggestText);
            }
        }
        return list;
    }

    @Override
    public List<String> phraseSuggest(String fieldName, String fieldValue, PhraseSuggestParam param, Class<T> clazz) throws Exception {
        MetaData metaData = elasticsearchIndex.getMetaData(clazz);
        String[] indexname = metaData.getSearchIndexNames();
        return phraseSuggest(fieldName, fieldValue, param, clazz, indexname);
    }



    @Override
    public List<String> phraseSuggest(String fieldName, String fieldValue, PhraseSuggestParam param, Class<T> clazz, String... indexs) throws Exception {
        if(param == null){
            //没指定参数，传入默认参数
            param = new PhraseSuggestParam(5,0,null,"always");
        }
        String[] indexname = indexs;
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        PhraseSuggestionBuilder phraseSuggestionBuilder = new PhraseSuggestionBuilder(fieldName);
        phraseSuggestionBuilder
                .text(fieldValue)
                .confidence(param.getConfidence())
                .size(Constant.COMPLETION_SUGGESTION_SIZE)
                .maxErrors(param.getMaxErrors())
                .addCandidateGenerator(new DirectCandidateGeneratorBuilder(fieldName).suggestMode(param.getSuggestMode()));
        if(param.getAnalyzer() != null) {
            phraseSuggestionBuilder.analyzer(param.getAnalyzer());
        }
        suggestBuilder.addSuggestion("suggest_" + fieldName, phraseSuggestionBuilder);
        searchSourceBuilder.suggest(suggestBuilder);
        SearchRequest searchRequest = new SearchRequest(indexname);
        searchRequest.source(searchSourceBuilder);
        logger.info(searchSourceBuilder.toString());
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        Suggest suggest = searchResponse.getSuggest();
        if (suggest == null) {
            return null;
        }
        PhraseSuggestion phraseSuggestion = suggest.getSuggestion("suggest_" + fieldName);
        List<String> list = new ArrayList<>();
        for (PhraseSuggestion.Entry entry : phraseSuggestion.getEntries()) {
            for (PhraseSuggestion.Entry.Option option : entry) {
                String suggestText = option.getText().string();
                list.add(suggestText);
            }
        }
        return list;
    }

    private int getTotalPages(long totalHits, int pageSize) {
        return pageSize == 0 ? 1 : (int) Math.ceil((double) totalHits / (double) pageSize);
    }

    public static class PhraseSuggestParam{
        private int maxErrors;
        private float confidence;
        private String analyzer;
        private String suggestMode;

        public PhraseSuggestParam(int maxErrors, float confidence, String analyzer, String suggestMode) {
            this.maxErrors = maxErrors;
            this.confidence = confidence;
            this.analyzer = analyzer;
            this.suggestMode = suggestMode;
        }

        public int getMaxErrors() {
            return maxErrors;
        }

        public void setMaxErrors(int maxErrors) {
            this.maxErrors = maxErrors;
        }

        public float getConfidence() {
            return confidence;
        }

        public void setConfidence(float confidence) {
            this.confidence = confidence;
        }

        public String getAnalyzer() {
            return analyzer;
        }

        public void setAnalyzer(String analyzer) {
            this.analyzer = analyzer;
        }

        public String getSuggestMode() {
            return suggestMode;
        }

        public void setSuggestMode(String suggestMode) {
            this.suggestMode = suggestMode;
        }
    }
}
