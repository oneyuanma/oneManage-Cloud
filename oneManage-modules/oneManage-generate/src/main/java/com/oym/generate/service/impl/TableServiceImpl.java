package com.oym.generate.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.cons.YesOrNoEnum;
import com.oym.commons.utils.Argument;
import com.oym.commons.utils.CollectionUtils;
import com.oym.generate.cons.FieldTypeEnum;
import com.oym.generate.cons.QueryTypeEnum;
import com.oym.generate.cons.ViewTypeEnum;
import com.oym.generate.ctrl.vo.TableSelectVO;
import com.oym.generate.ctrl.vo.TableVO;
import com.oym.generate.dao.FieldDao;
import com.oym.generate.dao.TableDao;
import com.oym.generate.dao.TableFieldDao;
import com.oym.generate.domain.bo.GetTablePageListBO;
import com.oym.generate.domain.bo.TableSaveBO;
import com.oym.generate.domain.dataobject.*;
import com.oym.generate.service.TableService;
import com.oym.generate.transform.TableTransform;
import com.oym.generate.utils.GenerateUtils;
import freemarker.template.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 表描述信息service实现
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
@Service
public class TableServiceImpl extends ServiceImpl<TableDao, TableDO> implements TableService {

    private static final Logger logger = LoggerFactory.getLogger(TableServiceImpl.class);

    @Autowired
    private TableDao tableDao;
    @Autowired
    private TableFieldDao tableFieldDao;
    @Autowired
    private FieldDao fieldDao;

    /**
     * 代码生成默认作者
     */
    @Value("${generate.config.author:oneyuanma}")
    private String author;
    /**
     * 代码生成默认生成路径
     */
    @Value("${generate.config.generatePath:/Users/jinjing/ONE源码/code}")
    private String generatePath;
    /**
     * 系统默认字段
     */
    @Value("${generate.config.sysDefaultColumn:id,create_date,update_date,remarks,del_flag}")
    private String sysDefaultColumn;

    /**
     * 分页获表描述信息对列表
     *
     * @param bo
     * @return
     */
    @Override
    public PageInfo<TableVO> pageList(GetTablePageListBO bo) {
        // 分页
        PageHelper.startPage(bo.getNowPageIndex(), bo.getPageSize());
        // 表描述信息列表
        List<TableDO> kvDOS = tableDao.list(new TableDO(bo.getTableName(), bo.getTableDescription(), bo.getAuthor()));
        // 构建返回分页对象
        PageInfo pageInfo = new PageInfo(kvDOS);
        pageInfo.setList(CollectionUtils.convert(kvDOS, TableTransform.INS::transfer));
        return pageInfo;
    }

    /**
     * 表描述信息保存
     *
     * @param bo
     * @return
     */
    @Override
    public OymResult<Boolean> save(TableSaveBO bo) {
        // 修改
        return OymResult.success(tableDao.updateById(TableTransform.INS.transfer(bo)) > 0);
    }

    /**
     * 表描述信息删除
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean delete(Long id) {
        TableDO tableDO = tableDao.selectById(id);
        // 根据表名获取与字段的关联关系
        List<TableFieldDO> tableFieldDOS = tableFieldDao.getByTableId(tableDO.getId());
        if (Argument.isNotEmpty(tableFieldDOS)) {
            // 遍历删除表与字段关联关系和字段信息
            tableFieldDOS.forEach(tableFieldDO -> {
                fieldDao.deleteById(tableFieldDO.getFieldId());
                tableFieldDao.deleteById(tableFieldDO.getId());
            });
        }
        // 删除表信息
        return tableDao.deleteById(id) > 0;
    }

    /**
     * 表信息同步
     *
     * @param tableName
     * @return
     */
    @Override
    public Boolean sync(String tableName) {
        String schema = "oneManage_cloud";
        if (Argument.isBlank(tableName)) {
            // 表名为空，同步所有表
            List<TableInfoDO> tableInfoDOS = tableDao.getTableInfoBySchema(schema);
            tableInfoDOS.forEach(tableInfoDO -> {
                syncExecute(schema, tableInfoDO.getTableName());
            });
        }
        return syncExecute(schema, tableName);
    }

    /**
     * 所有表名
     *
     * @return
     */
    @Override
    public List<String> tableList() {
        String schema = "oneManage_cloud";
        List<TableInfoDO> tableInfoDOS = tableDao.getTableInfoBySchema(schema);
        return CollectionUtils.asList(tableInfoDOS, TableInfoDO::getTableName);
    }

    /**
     * 表同步执行
     *
     * @param tableName
     * @return
     */
    @Transactional
    public Boolean syncExecute(String schema, String tableName) {
        if (Argument.isBlank(tableName)) {
            return false;
        }
        /**
         * 先删除之前表和字段的信息
         * 然后重新同步表盒字段信息
         */
        TableDO tableDO = tableDao.getOne(new TableDO(tableName));
        // 删除
        if (Argument.isNotNull(tableDO)) {
            // 根据表名获取与字段的关联关系
            List<TableFieldDO> tableFieldDOS = tableFieldDao.getByTableId(tableDO.getId());
            if (Argument.isNotEmpty(tableFieldDOS)) {
                // 遍历删除表与字段关联关系和字段信息
                tableFieldDOS.forEach(tableFieldDO -> {
                    fieldDao.deleteById(tableFieldDO.getFieldId());
                    tableFieldDao.deleteById(tableFieldDO.getId());
                });
            }
            // 删除表信息
            tableDao.deleteById(tableDO.getId());
        }
        // 新增
        TableDO table = new TableDO();
        TableInfoDO tableInfoDO = tableDao.getTableInfoBySchemaAndTable(schema, tableName);
        table.setTableName(tableInfoDO.getTableName());
        table.setTableDescription(tableInfoDO.getTableComment());
        table.setClassName(GenerateUtils.firstStrToUpperCase(tableInfoDO.getTableName()));
        // 模块名
        int index = tableInfoDO.getTableName().indexOf("_");
        if (index > -1) {
            table.setModuleName(tableInfoDO.getTableName().substring(0, index));
        } else {
            table.setModuleName(tableInfoDO.getTableName());
        }
        table.setFunctionName(tableInfoDO.getTableComment());
        table.setAuthor(author);
        table.setPath(generatePath);
        // 保存表信息
        tableDao.insert(table);
        List<FieldInfoDO> fieldInfoList = tableDao.getFieldInfoByTable(schema, tableName);
        List<String> defaultColumns = Lists.newArrayList(sysDefaultColumn.split(","));
        if (Argument.isEmpty(fieldInfoList)) {
            return false;
        }
        for (FieldInfoDO fieldInfo : fieldInfoList) {
            if (defaultColumns.contains(fieldInfo.getColumnName())) {
                continue;
            }
            FieldDO fieldDO = new FieldDO();
            fieldDO.setFieldName(fieldInfo.getColumnName());
            fieldDO.setFieldDescription(fieldInfo.getColumnComment());
            fieldDO.setDataType(fieldInfo.getColumnType());
            fieldDO.setLength(fieldInfo.getCharacterMaximumLength());
            fieldDO.setIfNull(fieldInfo.getIsNullable().equals("YES") ? YesOrNoEnum.NO.getValue() : YesOrNoEnum.YES.getValue());
            fieldDO.setIfList(YesOrNoEnum.YES.getValue());
            fieldDO.setIfEdit(YesOrNoEnum.YES.getValue());
            fieldDO.setIfQuery(YesOrNoEnum.YES.getValue());
            fieldDO.setQueryType(QueryTypeEnum.EQUAL.getValue());
            fieldDO.setViewType(ViewTypeEnum.TEXT_INPUT.getValue());
            fieldDO.setPosition(fieldInfo.getOrdinalPosition());
            // 转换成驼峰命名
            fieldDO.setFieldProperty(GenerateUtils.strReplaceToLowerCase(fieldInfo.getColumnName()));
            fieldDO.setFieldType(FieldTypeEnum.getName(fieldInfo.getDataType()));
            // 保存字段信息
            fieldDao.insert(fieldDO);

            TableFieldDO tableFieldDO = new TableFieldDO(table.getId(), fieldDO.getId());
            // 保存表和字段关联关系
            tableFieldDao.insert(tableFieldDO);
        }
        return true;
    }

    /**
     * 表下拉列表项
     *
     * @return
     */
    @Override
    public List<TableSelectVO> selectOptions() {
        List<TableSelectVO> tableSelectVOS = Lists.newArrayList();
        List<TableDO> tableDOS = tableDao.list(new TableDO());
        if (Argument.isEmpty(tableDOS)) {
            return tableSelectVOS;
        }
        tableDOS.forEach(table -> {
            TableSelectVO vo = new TableSelectVO();
            vo.setValue(table.getTableName());
            vo.setLabel(table.getTableName() + "【" + table.getTableDescription() + "】");
            tableSelectVOS.add(vo);
        });
        return tableSelectVOS;
    }

    /**
     * 代码生成
     *
     * @param tableId
     * @return
     */
    @Override
    public OymResult<Boolean> generate(Long tableId) {
        // 查询表信息
        TableDO table = tableDao.selectById(tableId);
        List<TableFieldDO> tableFieldDOS = tableFieldDao.getByTableId(tableId);
        List<FieldDO> fieldDOS = new ArrayList<>();
        // 遍历查询字段信息
        tableFieldDOS.forEach(tableFieldDO -> {
            fieldDOS.add(fieldDao.selectById(tableFieldDO.getFieldId()));
        });

        Map<String, Object> data = new HashMap<>();
        // 模板填充数据
        List<Map<String, Object>> columnList = new ArrayList<>();
        // 字段信息
        for (FieldDO field : fieldDOS) {
            Map<String, Object> oMap = new HashMap<>();
            oMap.put("fileName", field.getFieldName());
            oMap.put("columnName", field.getFieldProperty());
            oMap.put("upColumnName", GenerateUtils.firstStrToUpperCase(field.getFieldProperty()));// 列名称，首字母大写，去下划线
            oMap.put("doColumnName", GenerateUtils.firstStrToLowerCase(field.getFieldProperty()));// 列名称，首字母小写，去下划线
            oMap.put("columnType", field.getFieldType());
            oMap.put("columnComment", field.getFieldDescription());
            oMap.put("ifEdit", field.getIfEdit());
            oMap.put("ifList", field.getIfList());
            oMap.put("ifNull", field.getIfNull());
            oMap.put("ifQuery", field.getIfQuery());
            oMap.put("length", field.getLength());
            oMap.put("viewType", field.getViewType());
            oMap.put("queryType", field.getQueryType());
            columnList.add(oMap);
        }

        // 属性
        data.put("columns", columnList);
        data.put("author", table.getAuthor());
        data.put("tableName", table.getTableName());
        data.put("className", table.getClassName());
        data.put("classNameParam", GenerateUtils.firstStrToLowerCase(table.getClassName()));
        data.put("mouldName", table.getModuleName());
        data.put("functionComment", table.getFunctionName());
        data.put("date", DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now()));

        /**
         * java生成
         */
        // 生成DO
        creatDO(table, data);
        // 生成dao
        creatDao(table, data);
        // 生成删除request
        creatDeleteReq(table, data);
        // 生成保存reqeust
        creatSaveReq(table, data);
        // 生成分页查询request
        creatPageReq(table, data);
        // 生成分页列表vo
        creatVo(table, data);
        // 生成保存bo
        creatSaveBo(table, data);
        // 生成分页bo
        creatPageBo(table, data);
        // 生成实体转换类
        creatTrans(table, data);
        // 生成service
        creatService(table, data);
        // 生成service impl
        creatServiceImpl(table, data);
        // 生成ctl
        createCtrl(table, data);
        // 生成xml
        createXml(table, data);

        /**
         * 前端生成
         */
        // 生成js
        createJs(table, data);
        // 生成vue页面
        createVue(table, data);
        return OymResult.success(true);
    }

    /**
     * 生成DO
     *
     * @param table
     * @param data
     */
    private void creatDO(TableDO table, Map<String, Object> data) {
        try {
            // 模板名称
            String ftlName = "/DataObject.ftl";
            // 生成文件的路径和名称
            String fileName = table.getPath() + "/" + table.getTableName() + "/src/com/oym/" + table.getModuleName() + "/domain/dataobject/" + table.getClassName() + "DO.java";
            creatTemplate(data, ftlName, fileName);
        } catch (Exception e) {
            logger.error("TableServiceImpl creatDO tableName:{}, err, msg:{}", table.getTableName(), ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 生成Dao
     *
     * @param table
     */
    private void creatDao(TableDO table, Map<String, Object> data) {
        try {
            // 模板名称
            String ftlName = "/Dao.ftl";
            // 生成文件的路径和名称
            String fileName = table.getPath() + "/" + table.getTableName() + "/src/com/oym/" + table.getModuleName() + "/dao/" + table.getClassName() + "Dao.java";
            creatTemplate(data, ftlName, fileName);
        } catch (Exception e) {
            logger.error("TableServiceImpl creatDao tableName:{}, err, msg:{}", table.getTableName(), ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 生成删除request
     *
     * @param table
     */
    private void creatDeleteReq(TableDO table, Map<String, Object> data) {
        try {
            // 模板名称
            String ftlName = "/DeleteRequest.ftl";
            // 生成文件的路径和名称
            String fileName = table.getPath() + "/" + table.getTableName() + "/src/com/oym/" + table.getModuleName() + "/ctrl/request/" + table.getClassName() + "DeleteRequest.java";
            creatTemplate(data, ftlName, fileName);
        } catch (Exception e) {
            logger.error("TableServiceImpl creatDeleteReq tableName:{}, err, msg:{}", table.getTableName(), ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 生成保存request
     *
     * @param table
     */
    private void creatSaveReq(TableDO table, Map<String, Object> data) {
        try {
            // 模板名称
            String ftlName = "/SaveRequest.ftl";
            // 生成文件的路径和名称
            String fileName = table.getPath() + "/" + table.getTableName() + "/src/com/oym/" + table.getModuleName() + "/ctrl/request/" + table.getClassName() + "SaveRequest.java";
            creatTemplate(data, ftlName, fileName);
        } catch (Exception e) {
            logger.error("TableServiceImpl creatSaveReq tableName:{}, err, msg:{}", table.getTableName(), ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 生成分页列表request
     *
     * @param table
     */
    private void creatPageReq(TableDO table, Map<String, Object> data) {
        try {
            // 模板名称
            String ftlName = "/PageRequest.ftl";
            // 生成文件的路径和名称
            String fileName = table.getPath() + "/" + table.getTableName() + "/src/com/oym/" + table.getModuleName() + "/ctrl/request/Get" + table.getClassName() + "PageListRequest.java";
            creatTemplate(data, ftlName, fileName);
        } catch (Exception e) {
            logger.error("TableServiceImpl creatPageReq tableName:{}, err, msg:{}", table.getTableName(), ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 生成分页列表VO
     *
     * @param table
     */
    private void creatVo(TableDO table, Map<String, Object> data) {
        try {
            // 模板名称
            String ftlName = "/Vo.ftl";
            // 生成文件的路径和名称
            String fileName = table.getPath() + "/" + table.getTableName() + "/src/com/oym/" + table.getModuleName() + "/ctrl/vo/" + table.getClassName() + "VO.java";
            creatTemplate(data, ftlName, fileName);
        } catch (Exception e) {
            logger.error("TableServiceImpl creatoVo tableName:{}, err, msg:{}", table.getTableName(), ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 生成保存BO
     *
     * @param table
     */
    private void creatSaveBo(TableDO table, Map<String, Object> data) {
        try {
            // 模板名称
            String ftlName = "/SaveBo.ftl";
            // 生成文件的路径和名称
            String fileName = table.getPath() + "/" + table.getTableName() + "/src/com/oym/" + table.getModuleName() + "/domain/bo/" + table.getClassName() + "SaveBO.java";
            creatTemplate(data, ftlName, fileName);
        } catch (Exception e) {
            logger.error("TableServiceImpl creatSaveBo tableName:{}, err, msg:{}", table.getTableName(), ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 生成保存BO
     *
     * @param table
     */
    private void creatPageBo(TableDO table, Map<String, Object> data) {
        try {
            // 模板名称
            String ftlName = "/PageBo.ftl";
            // 生成文件的路径和名称
            String fileName = table.getPath() + "/" + table.getTableName() + "/src/com/oym/" + table.getModuleName() + "/domain/bo/Get" + table.getClassName() + "PageListBO.java";
            creatTemplate(data, ftlName, fileName);
        } catch (Exception e) {
            logger.error("TableServiceImpl creatSaveBo tableName:{}, err, msg:{}", table.getTableName(), ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 生成实体转换类
     *
     * @param table
     */
    private void creatTrans(TableDO table, Map<String, Object> data) {
        try {
            // 模板名称
            String ftlName = "/Trans.ftl";
            // 生成文件的路径和名称
            String fileName = table.getPath() + "/" + table.getTableName() + "/src/com/oym/" + table.getModuleName() + "/transform/" + table.getClassName() + "Transform.java";
            creatTemplate(data, ftlName, fileName);
        } catch (Exception e) {
            logger.error("TableServiceImpl creatTrans tableName:{}, err, msg:{}", table.getTableName(), ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 生成service
     *
     * @param table
     */
    private void creatService(TableDO table, Map<String, Object> data) {
        try {
            // 模板名称
            String ftlName = "/Service.ftl";
            // 生成文件的路径和名称
            String fileName = table.getPath() + "/" + table.getTableName() + "/src/com/oym/" + table.getModuleName() + "/service/" + table.getClassName() + "Service.java";
            creatTemplate(data, ftlName, fileName);
        } catch (Exception e) {
            logger.error("TableServiceImpl creatService tableName:{}, err, msg:{}", table.getTableName(), ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 生成service实现
     *
     * @param table
     */
    private void creatServiceImpl(TableDO table, Map<String, Object> data) {
        try {
            // 模板名称
            String ftlName = "/ServiceImpl.ftl";
            // 生成文件的路径和名称
            String fileName = table.getPath() + "/" + table.getTableName() + "/src/com/oym/" + table.getModuleName() + "/service/impl/" + table.getClassName() + "ServiceImpl.java";
            creatTemplate(data, ftlName, fileName);
        } catch (Exception e) {
            logger.error("TableServiceImpl creatServiceImpl tableName:{}, err, msg:{}", table.getTableName(), ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 生成ctrl
     *
     * @param table
     */
    private void createCtrl(TableDO table, Map<String, Object> data) {
        try {
            // 模板名称
            String ftlName = "/Ctrl.ftl";
            // 生成文件的路径和名称
            String fileName = table.getPath() + "/" + table.getTableName() + "/src/com/oym/" + table.getModuleName() + "/ctrl/" + table.getClassName() + "Controller.java";
            creatTemplate(data, ftlName, fileName);
        } catch (Exception e) {
            logger.error("TableServiceImpl createCtrl tableName:{}, err, msg:{}", table.getTableName(), ExceptionUtils.getStackTrace(e));
        }
    }


    /**
     * 生成xml
     *
     * @param table
     */
    private void createXml(TableDO table, Map<String, Object> data) {
        try {
            // 模板名称
            String ftlName = "/Xml.ftl";
            // 生成文件的路径和名称
            String fileName = table.getPath() + "/" + table.getTableName() + "/src/mapper/" + table.getClassName() + "Mapper.xml";
            creatTemplate(data, ftlName, fileName);
        } catch (Exception e) {
            logger.error("TableServiceImpl createXml tableName:{}, err, msg:{}", table.getTableName(), ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 生成前端api js
     *
     * @param table
     */
    private void createJs(TableDO table, Map<String, Object> data) {
        try {
            // 模板名称
            String ftlName = "/Js.ftl";
            // 生成文件的路径和名称
            String fileName = table.getPath() + "/" + table.getTableName() + "/ui/js/" + data.get("classNameParam") + ".js";
            creatTemplate(data, ftlName, fileName);
        } catch (Exception e) {
            logger.error("TableServiceImpl createJs tableName:{}, err, msg:{}", table.getTableName(), ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 生成前端vue 页面
     *
     * @param table
     */
    private void createVue(TableDO table, Map<String, Object> data) {
        try {
            // 模板名称
            String ftlName = "/Vue.ftl";
            // 生成文件的路径和名称
            String fileName = table.getPath() + "/" + table.getTableName() + "/ui/vue/" + "index.vue";
            creatTemplate(data, ftlName, fileName);
        } catch (Exception e) {
            logger.error("TableServiceImpl createJs tableName:{}, err, msg:{}", table.getTableName(), ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 生成文件
     *
     * @param data     数据模型
     * @param ftlName  ftl模板文件名称
     * @param fileName 生成文件的文件名称 例如：d:\TAction.java
     * @throws IOException
     * @throws TemplateException
     */
    public void creatTemplate(Map<String, Object> data, String ftlName, String fileName) throws IOException, TemplateException {

        Configuration cfg = new Configuration();

        // 设置字符集编码
        cfg.setEncoding(Locale.CHINA, "UTF-8");

        String ftlPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "template";

        // 加载模板文件
        cfg.setDirectoryForTemplateLoading(new File(ftlPath));

        // 设置对象包装器
        cfg.setObjectWrapper(new DefaultObjectWrapper());

        // 设计异常处理器
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

        // 获取指定模板文件
        Template template = cfg.getTemplate(ftlName);

        // 检查路径是否存在，不存在则创建
        String path = fileName.substring(0, fileName.lastIndexOf("."));

        // 目标文件
        File saveFile = new File(path);
        // 判断存放路径是否存在，存在就删除，不存在就创建
        if (saveFile.exists()) {
            saveFile.delete();
        } else {
            saveFile.getParentFile().mkdirs();
        }

        // 定义输入文件，默认生成在工程根目录，设置文件的编码格式
        Writer out = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");

        // 最后开始生成
        template.process(data, out);

        // 关闭资源，否则删除不掉
        out.close();
    }
}
