package com.oym.system.service;


import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResult;
import com.oym.system.ctrl.vo.KvVO;
import com.oym.system.domain.bo.GetKvPageListBO;
import com.oym.system.domain.bo.KvSaveBO;

/**
 * 系统键值对service
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
public interface KvService {

    /**
     * 分页获取键值对列表
     *
     * @param bo
     * @return
     */
    PageInfo<KvVO> pageList(GetKvPageListBO bo);

    /**
     * 键值对保存
     *
     * @param bo
     * @return
     */
    OymResult<Boolean> save(KvSaveBO bo);

    /**
     * 键值对删除
     *
     * @param id
     * @return
     */
    Boolean delete(Long id);

    /**
     * 根据key获取value
     * @param key
     * @return
     */
    String getValueByKey(String key);
}
