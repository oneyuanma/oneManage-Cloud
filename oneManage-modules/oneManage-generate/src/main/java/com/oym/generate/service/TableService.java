package com.oym.generate.service;


import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResult;
import com.oym.generate.ctrl.vo.TableSelectVO;
import com.oym.generate.ctrl.vo.TableVO;
import com.oym.generate.domain.bo.GetTablePageListBO;
import com.oym.generate.domain.bo.TableSaveBO;

import java.util.List;

/**
 * 表描述信息service
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
public interface TableService {

    /**
     * 分页获取表描述信息列表
     *
     * @param bo
     * @return
     */
    PageInfo<TableVO> pageList(GetTablePageListBO bo);

    /**
     * 表描述信息保存
     *
     * @param bo
     * @return
     */
    OymResult<Boolean> save(TableSaveBO bo);

    /**
     * 表描述信息删除
     *
     * @param id
     * @return
     */
    Boolean delete(Long id);

    /**
     * 表信息同步
     *
     * @param tableName
     * @return
     */
    Boolean sync(String tableName);

    /**
     * 所有表名
     *
     * @return
     */
    List<String> tableList();

    /**
     * 代码生成
     *
     * @param tableId
     * @return
     */
    OymResult<Boolean> generate(Long tableId);

    /**
     * 表下拉列表项
     * @return
     */
    List<TableSelectVO> selectOptions();
}
