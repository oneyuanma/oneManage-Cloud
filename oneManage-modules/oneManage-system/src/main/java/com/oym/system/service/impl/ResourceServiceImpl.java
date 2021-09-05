package com.oym.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.cons.ReturnCode;
import com.oym.commons.utils.Argument;
import com.oym.commons.utils.CollectionUtils;
import com.oym.system.cons.ResourceTypeEnum;
import com.oym.system.ctrl.vo.ResourceTreeVO;
import com.oym.system.dao.ResourceDao;
import com.oym.system.domain.bo.ResourceSaveBO;
import com.oym.system.domain.dataobject.ResourceDO;
import com.oym.system.service.ResourceService;
import com.oym.system.transform.ResourceTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 系统资源权限service实现
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceDao, ResourceDO> implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    /**
     * 默认顶级资源的父资源为0
     */
    private static final Long DEFAULT_PARENT_ID = 0L;

    /**
     * 默认顶级资源的父资源名称为
     */
    private static final String DEFAULT_TOP_PARENT_NAME = "无";

    /**
     * LAYOUT
     */
    private static final String LAYOUT = "Layout";

    /**
     * 默认第一层为1
     */
    private static final Integer FIRST_LEVEL = 1;

    @Override
    public List<ResourceTreeVO> treeList() {

        List<ResourceTreeVO> resourceTreeVOS = Lists.newArrayList();

        // 获取第一层级资源
        List<ResourceDO> topResources = resourceDao.listByParentId(DEFAULT_PARENT_ID);

        if (Argument.isEmpty(topResources)) {
            return resourceTreeVOS;
        }

        resourceTreeVOS = CollectionUtils.convert(topResources, ResourceTransform.INS::transfer);

        // 查询用户和角色的关联关系
        List<ResourceDO> allResources = resourceDao.list(new ResourceDO());

        Map<Long, String> id2TitleMap = CollectionUtils.toMap(allResources, ResourceDO::getId, ResourceDO::getTitle);

        for (ResourceTreeVO resourceTreeVO : resourceTreeVOS) {
            resourceTreeVO.setParentTitle(DEFAULT_TOP_PARENT_NAME);
            recursion(resourceTreeVO, id2TitleMap);
        }

        return resourceTreeVOS;
    }

    /**
     * 递归资源权限
     *
     * @param resourceTreeVO
     */
    public void recursion(ResourceTreeVO resourceTreeVO, Map<Long, String> id2TitleMap) {

        List<ResourceDO> resourceDOS = resourceDao.listByParentId(resourceTreeVO.getId());

        if (Argument.isNotEmpty(resourceDOS)) {

            List<ResourceTreeVO> resourceTreeVOS = CollectionUtils.convert(resourceDOS, ResourceTransform.INS::transfer);

            resourceTreeVO.setChildren(resourceTreeVOS);

            for (ResourceTreeVO vo : resourceTreeVOS) {

                vo.setParentTitle(id2TitleMap.get(vo.getParentId()));

                recursion(vo, id2TitleMap);
            }

        }

    }

//    @Override
//    public List<ResourceSelectTreeVO> treeSelect() {
//
//        List<ResourceSelectTreeVO> resourceSelectTreeVOS = Lists.newArrayList();
//
//        // 获取第一层级资源
//        List<ResourceDO> topResources = resourceDao.listByParentId(DEFAULT_PARENT_ID);
//
//        if (Argument.isEmpty(topResources)) {
//            return resourceSelectTreeVOS;
//        }
//
//        resourceSelectTreeVOS = CollectionUtils.convert(topResources, ResourceTransform.INS::transfer1);
//
//        for (ResourceSelectTreeVO resourceTreeVO : resourceSelectTreeVOS) {
//            recursion1(resourceTreeVO);
//        }
//
//        return resourceSelectTreeVOS;
//
//    }

//    /**
//     * 递归资源权限
//     *
//     * @param resourceTreeVO
//     */
//    public void recursion1(ResourceSelectTreeVO resourceTreeVO) {
//
//        List<ResourceDO> resourceDOS = resourceDao.listByParentId(resourceTreeVO.getResId());
//
//        if (Argument.isNotEmpty(resourceDOS)) {
//
//            List<ResourceSelectTreeVO> resourceTreeVOS = CollectionUtils.convert(resourceDOS, ResourceTransform.INS::transfer1);
//
//            resourceTreeVO.setChildren(resourceTreeVOS);
//
//            for (ResourceSelectTreeVO vo : resourceTreeVOS) {
//                recursion1(vo);
//            }
//
//        }
//
//    }

    @Override
    public OymResult<Boolean> save(ResourceSaveBO bo) {

        // 上级菜单如果没有，默认顶级菜单
        if (Argument.isNull(bo.getParentId())) {
            bo.setParentId(DEFAULT_PARENT_ID);
        }

        // bo -> do
        ResourceDO resource = ResourceTransform.INS.transfer(bo);

        // 层级设置 + 上级资源component 设置
        ResourceDO parent = resourceDao.selectById(bo.getParentId());
        if (Argument.isNull(parent)) {
            resource.setLevel(FIRST_LEVEL);
        } else {
            resource.setLevel(parent.getLevel() + 1);

            // 如果下级是菜单，自动设置组件为Layout
            if (ResourceTypeEnum.isMenu(resource.getType())) {
                parent.setComponent(LAYOUT);
                resourceDao.updateById(parent);
            }
        }

        // 根据资源code获取资源条数
        int count = 0;
        if (Argument.isNotBlank(bo.getCode())) {
            count = resourceDao.getCountByCode(bo.getCode());
        }

        // 修改
        if (Argument.isNotNull(bo.getId())) {
            if (count > 1) {
                return OymResult.error(ReturnCode.RESOURCE_CODE_EXISTED);
            }
            if (bo.getParentId().equals(bo.getId())) {
                return OymResult.error("父级资源不能是自己");
            }
            return OymResult.success(resourceDao.updateById(resource) > 0);
        }

        // 新增
        if (count > 0) {
            return OymResult.error(ReturnCode.RESOURCE_CODE_EXISTED);
        }
        return OymResult.success(resourceDao.insert(resource) > 0);

    }

    @Override
    public Boolean delete(Long id) {
        // 递归删除下级资源
        QueryWrapper<ResourceDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("parent_id", id);
        List<ResourceDO> list = resourceDao.selectList(queryWrapper);

        if (Argument.isNotEmpty(list)) {
            resourceDao.deleteBatchIds(CollectionUtils.asList(list, ResourceDO::getId));
            recursionDel(list);
        }

        return resourceDao.deleteById(id) > 0;
    }

    /**
     * 递归删除资源权限
     *
     * @param resourceDOList
     */
    public void recursionDel(List<ResourceDO> resourceDOList) {

        resourceDOList.forEach(resourceDO -> {
            resourceDao.deleteById(resourceDO.getId());

            // 查询下级资源
            QueryWrapper<ResourceDO> queryWrapper = Wrappers.query();
            queryWrapper.eq("parent_id", resourceDO.getId());
            List<ResourceDO> list = resourceDao.selectList(queryWrapper);

            if (Argument.isNotEmpty(list)) {
                resourceDao.deleteBatchIds(CollectionUtils.asList(list, ResourceDO::getId));
                recursionDel(list);
            }
        });
    }

}
