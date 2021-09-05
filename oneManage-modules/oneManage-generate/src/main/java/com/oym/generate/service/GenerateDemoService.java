package com.oym.generate.service;


import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResult;
import com.oym.generate.ctrl.vo.GenerateDemoVO;
import com.oym.generate.domain.bo.GenerateDemoSaveBO;
import com.oym.generate.domain.bo.GetGenerateDemoPageListBO;


/**
 * 代码生成工具演示demoservice
 *
 * @author oneyuanma
 * @Date 2021/08/12
 */
public interface GenerateDemoService {

    /**
     * 分页获取代码生成工具演示demo列表
     *
     * @param bo
     * @return
     */
    PageInfo<GenerateDemoVO> pageList(GetGenerateDemoPageListBO bo);

    /**
     * 代码生成工具演示demo保存
     *
     * @param bo
     * @return
     */
    OymResult<Boolean> save(GenerateDemoSaveBO bo);

    /**
     * 代码生成工具演示demo删除
     *
     * @param id
     * @return
     */
    Boolean delete(Long id);


}
