package com.oym.log.ctrl;


import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResponse;
import com.oym.commons.cons.OperateTypeEnum;
import com.oym.commons.utils.Argument;
import com.oym.commons.utils.CollectionUtils;
import com.oym.log.cons.LogIndexCons;
import com.oym.log.ctrl.request.GetLoginLogListRequest;
import com.oym.log.ctrl.request.GetOperateLogDetailRequest;
import com.oym.log.ctrl.request.GetOperateLogListRequest;
import com.oym.log.ctrl.vo.LoginLogVO;
import com.oym.log.ctrl.vo.OperateLogVO;
import com.oym.log.domain.bo.GetLoginLogPageListBO;
import com.oym.log.domain.bo.GetOperateLogPageListBO;
import com.oym.log.domain.dto.LoginLogDTO;
import com.oym.log.domain.dto.OperateLogDTO;
import com.oym.log.service.LogLoginService;
import com.oym.log.service.LogOperateService;
import com.oym.log.transform.LogTransform;
import com.oym.system.api.KvApi;
import com.oym.system.api.domain.request.GetStringValueRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zxp.esclientrhl.repository.ElasticsearchTemplate;
import org.zxp.esclientrhl.repository.PageList;
import org.zxp.esclientrhl.repository.PageSortHighLight;
import org.zxp.esclientrhl.repository.Sort;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统日志
 *
 * @author oneyuanma
 * @date 2021/06/23
 */
@RestController
@RequestMapping("/log")
@Api(value = "系统日志API", tags = {"系统日志API"})
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private LogLoginService logLoginService;

    @Autowired
    private LogOperateService logOperateService;

    @Autowired
    private KvApi kvApi;

    /**
     * 日志保存方式
     */
    private static final String KAFKA = "kafka";

    @ApiOperation(value = "登录日志列表")
    @PostMapping("/login/pageList")
    public OymResponse<PageInfo<LoginLogVO>> pageList(@RequestBody GetLoginLogListRequest request) {

        String value = kvApi.getStringValue(new GetStringValueRequest("login_log_type", "kafka"));

        if (KAFKA.equals(value)) {
            PageInfo<LoginLogVO> pageList = new PageInfo<>();
            // 组成es查询条件
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (Argument.isNotBlank(request.getTitle())) {
                boolQueryBuilder.must(QueryBuilders.termQuery("title.keyword", request.getTitle()));
            }
            if (Argument.isNotBlank(request.getUserName())) {
                boolQueryBuilder.must(QueryBuilders.termQuery("userName.keyword", request.getUserName()));
            }

            // es分页排序
            PageSortHighLight pageSortHighLight = new PageSortHighLight(request.getNowPageIndex(), request.getPageSize(),
                    new Sort(new Sort.Order(SortOrder.DESC, "time")));

            try {
                PageList<LoginLogDTO> page = elasticsearchTemplate.search(boolQueryBuilder, pageSortHighLight, LoginLogDTO.class, LogIndexCons.INDEX_LOGIN_LOG);
                pageList.setList(CollectionUtils.convert(page.getList(), LogTransform.INS::transfer));
                pageList.setTotal(page.getTotalElements());
                pageList.setPageSize(page.getPageSize());
                return OymResponse.success(pageList);
            } catch (Exception e) {
                logger.error("LogController login log search err, msg:{}", ExceptionUtils.getStackTrace(e));
            }

            return OymResponse.success(pageList);
        } else {
            GetLoginLogPageListBO bo = new GetLoginLogPageListBO(request.getTitle(), request.getUserName());
            bo.setNowPageIndex(request.getNowPageIndex());
            bo.setPageSize(request.getPageSize());
            return OymResponse.success(logLoginService.pageList(bo));
        }
    }

    @ApiOperation(value = "操作日志列表")
    @PostMapping("/operate/pageList")
    public OymResponse<PageInfo<OperateLogVO>> operatePageList(@RequestBody GetOperateLogListRequest request) {
        String value = kvApi.getStringValue(new GetStringValueRequest("operate_log_type", "kafka"));
        if (KAFKA.equals(value)) {
            PageInfo<OperateLogVO> pageList = new PageInfo<>();

            // 组成es查询条件
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (Argument.isNotBlank(request.getModule())) {
                boolQueryBuilder.must(QueryBuilders.termQuery("module.keyword", request.getModule()));
            }
            if (Argument.isNotBlank(request.getOparatePath())) {
                boolQueryBuilder.must(QueryBuilders.termQuery("operatePah.keyword", request.getOparatePath()));
            }

            // es分页排序
            PageSortHighLight pageSortHighLight = new PageSortHighLight(request.getNowPageIndex(), request.getPageSize(),
                    new Sort(new Sort.Order(SortOrder.DESC, "time")));

            try {
                PageList<OperateLogDTO> page = elasticsearchTemplate.search(boolQueryBuilder, pageSortHighLight, OperateLogDTO.class, LogIndexCons.INDEX_OPERATE_LOG);
                List<OperateLogVO> list = CollectionUtils.convert(page.getList(), LogTransform.INS::transfer);
                list.forEach(operateLogVO -> {
                    operateLogVO.setType(OperateTypeEnum.getName(operateLogVO.getType()));
                });
                pageList.setList(list);
                pageList.setTotal(page.getTotalElements());
                return OymResponse.success(pageList);
            } catch (Exception e) {
                logger.error("LogController operate log search err, msg:{}", ExceptionUtils.getStackTrace(e));
            }

            return OymResponse.success(pageList);
        } else {
            GetOperateLogPageListBO bo = new GetOperateLogPageListBO(request.getModule(), request.getOparatePath());
            bo.setNowPageIndex(request.getNowPageIndex());
            bo.setPageSize(request.getPageSize());
            return OymResponse.success(logOperateService.pageList(bo));
        }
    }

    @ApiOperation(value = "操作日志详情")
    @PostMapping("/operate/detail")
    public OymResponse<OperateLogVO> operateDetil(@RequestBody GetOperateLogDetailRequest request) {
        PageList<OperateLogVO> pageList = new PageList<>();

        // 组成es查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (Argument.isNotBlank(request.getId())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("id.keyword", request.getId()));
        }

        try {
            List<OperateLogDTO> list = elasticsearchTemplate.search(boolQueryBuilder, OperateLogDTO.class, LogIndexCons.INDEX_OPERATE_LOG);
            return OymResponse.success(LogTransform.INS.transfer(list.get(0)));
        } catch (Exception e) {
            logger.error("LogController operate log search err, msg:{}", ExceptionUtils.getStackTrace(e));
        }

        return OymResponse.success();
    }
}
