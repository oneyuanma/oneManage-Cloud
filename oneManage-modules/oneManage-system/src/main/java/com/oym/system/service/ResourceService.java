package com.oym.system.service;


import com.oym.commons.base.response.OymResult;
import com.oym.system.ctrl.vo.ResourceTreeVO;
import com.oym.system.domain.bo.ResourceSaveBO;

import java.util.List;

/**
 * 系统资源权限service
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
public interface ResourceService {

    /**
     * 资源权限树形数据列表
     *
     * @return
     */
    List<ResourceTreeVO> treeList();

//    /**
//     * 资源权限下拉树形列表数据
//     *
//     * @return
//     */
//    List<ResourceSelectTreeVO> treeSelect();

    /**
     * 资源权限保存保存
     *
     * @return
     */
    OymResult<Boolean> save(ResourceSaveBO bo);

    /**
     * 资源删除
     * @return
     */
    Boolean delete(Long id);
}
