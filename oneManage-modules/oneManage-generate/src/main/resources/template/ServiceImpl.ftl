package com.oym.${mouldName}.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.cons.YesOrNoEnum;
import com.oym.commons.utils.Argument;
import com.oym.commons.utils.CollectionUtils;
import com.oym.${mouldName}.ctrl.vo.${className}VO;
import com.oym.${mouldName}.dao.${className}Dao;
import com.oym.${mouldName}.domain.bo.Get${className}PageListBO;
import com.oym.${mouldName}.domain.bo.${className}SaveBO;
import com.oym.${mouldName}.domain.dataobject.*;
import com.oym.${mouldName}.service.${className}Service;
import com.oym.${mouldName}.transform.${className}Transform;
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
 * ${functionComment}service实现
 *
 * @author ${author}
 * @Date ${date}
 */
@Service
public class ${className}ServiceImpl extends ServiceImpl<${className}Dao, ${className}DO> implements ${className}Service {

    private static final Logger logger = LoggerFactory.getLogger(${className}ServiceImpl.class);

    @Autowired
    private ${className}Dao ${classNameParam}Dao;

    /**
    * 分页获取${functionComment}列表
    *
    * @param bo
    * @return
    */
    @Override
    public PageInfo<${className}VO> pageList(Get${className}PageListBO bo) {
        // 分页
        PageHelper.startPage(bo.getNowPageIndex(), bo.getPageSize());
        // ${functionComment}列表
        ${className}DO ${classNameParam}DO = new ${className}DO();
    <#list columns as c>
    <#if c.ifQuery == "true">
        ${classNameParam}DO.set${ c.upColumnName}(bo.get${ c.upColumnName}());
    </#if>
    </#list>
        List<${className}DO> ${classNameParam}DOS = ${classNameParam}Dao.list(${classNameParam}DO);
        // 构建返回分页对象
        PageInfo pageInfo = new PageInfo(${classNameParam}DOS);
        pageInfo.setList(CollectionUtils.convert(${classNameParam}DOS, ${className}Transform.INS::transfer));
        return pageInfo;
    }

    /**
     * ${functionComment}保存
     *
     * @param bo
     * @return
    */
    @Override
    public OymResult<Boolean> save(${className}SaveBO bo) {
        // 修改
        if (Argument.isNotNull(bo.getId())) {
            return OymResult.success(${classNameParam}Dao.updateById(${className}Transform.INS.transfer(bo)) > 0);
        }
        // 新增
        return OymResult.success(${classNameParam}Dao.insert(${className}Transform.INS.transfer(bo)) > 0);
    }

    /**
     * ${functionComment}删除
     *
     * @param id
     * @return
     */
    @Override
    public Boolean delete(Long id) {
        return ${classNameParam}Dao.deleteById(id) > 0;
    }

}
