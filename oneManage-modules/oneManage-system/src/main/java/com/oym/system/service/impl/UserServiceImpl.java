package com.oym.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.cons.ReturnCode;
import com.oym.commons.user.UserLoginInfo;
import com.oym.commons.utils.Argument;
import com.oym.commons.utils.CollectionUtils;
import com.oym.system.cons.ResourceTypeEnum;
import com.oym.system.cons.UserStatusEnum;
import com.oym.system.ctrl.vo.ResourceTreeVO;
import com.oym.system.ctrl.vo.UserInfoVO;
import com.oym.system.ctrl.vo.UserVO;
import com.oym.system.dao.*;
import com.oym.system.domain.bo.GetUserPageListBO;
import com.oym.system.domain.bo.UserRoleSaveBO;
import com.oym.system.domain.bo.UserSaveBO;
import com.oym.system.domain.dataobject.*;
import com.oym.system.domain.dto.UserRoleDTO;
import com.oym.system.domain.tuple.Tuple2;
import com.oym.system.service.UserRoleService;
import com.oym.system.service.UserService;
import com.oym.system.transform.ResourceTransform;
import com.oym.system.transform.UserTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统用户service实现
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserDO> implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleResourceDao roleResourceDao;
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 默认顶级资源的父资源为0
     */
    private static final Long DEFAULT_PARENT_ID = 0L;

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    @Override
    public UserLoginInfo getUserByName(String username) {
        return UserTransform.INS.transfer(userDao.getUserByName(username));
    }

    /**
     * 保存用户登录信息
     *
     * @param userId
     * @param ip
     * @return
     */
    @Override
    public Boolean saveLoginInfo(Long userId, String ip) {
        UserDO user = userDao.selectById(userId);
        user.setLastLoginIp(user.getNowLoginIp());
        user.setNowLoginIp(ip);
        user.setLastLoginTime(user.getNowLoginTime());
        user.setNowLoginTime(LocalDateTime.now());
        return userDao.updateById(user) > 0;
    }

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfoVO getUserInfo(Long userId) {
        // 用户个人信息
        UserDO userDO = userDao.selectById(userId);
        UserInfoVO userInfoVO = UserTransform.INS.transfer1(userDO);

        // 用户角色权限信息
        // 角色编码集合
        Set<String> codes = Sets.newHashSet();
        userInfoVO.setRoles(codes);

        // 权限集合
        Set<String> permissions = Sets.newHashSet();
        userInfoVO.setPermissions(permissions);

        // 拥有权限的菜单集合
        List<ResourceTreeVO> menus = Lists.newArrayList();
        userInfoVO.setMenus(menus);

        // 根据用户id获取他拥有的所有权限资源
        Tuple2<List<ResourceDO>, String> tuple2 = getResourceByUser(userId);

        List<ResourceDO> resources = tuple2.getT0();
        userInfoVO.setRoleNames(tuple2.getT1());

        if (Argument.isEmpty(resources)) {
            return userInfoVO;
        }

        for (ResourceDO resourceDO : resources) {
            if (ResourceTypeEnum.isMenu(resourceDO.getType())) {
                codes.add(resourceDO.getCode());
                menus.add(ResourceTransform.INS.transfer2TreeVO(resourceDO));

            } else if (ResourceTypeEnum.isButton(resourceDO.getType())) {
                permissions.add(resourceDO.getCode());
            }
        }

        // 排序
        Set<ResourceTreeVO> ResourceTreeVOSet = this.buildTree(new HashSet(userInfoVO.getMenus()));
        List<ResourceTreeVO> list = new ArrayList(ResourceTreeVOSet);
        list = list.stream().sorted(Comparator.comparing(ResourceTreeVO::getSortNo)).collect(Collectors.toList());
        sort(list);
        userInfoVO.setMenus(list);
        return userInfoVO;
    }

    /**
     * 根据用户id获取他拥有的所有权限资源
     *
     * @param userId
     * @return
     */
    private Tuple2<List<ResourceDO>, String> getResourceByUser(Long userId) {
        // 所有权限资源集合
        List<ResourceDO> resourceDOS = Lists.newArrayList();
        // 角色名称, 逗号隔开
        String roleNames = "";

        // 根据用户id获取他（她）的所有角色
        OymResult<List<UserRoleDTO>> result = userRoleService.getByUsers(Arrays.asList(userId));
        if (result.isFail()) {
            return Tuple2.of(resourceDOS, roleNames);
        }
        List<UserRoleDTO> userRoleDTOS = result.getData();
        if (Argument.isEmpty(userRoleDTOS)) {
            return Tuple2.of(resourceDOS, roleNames);
        }

        // 根据角色获取用户所有权限信息
        for (UserRoleDTO userRole : userRoleDTOS) {
            RoleDO role = roleDao.selectById(userRole.getRoleId());
            roleNames += role.getName() + ",";

            QueryWrapper<RoleResourceDO> query = Wrappers.query();
            query.eq("role_id", userRole.getRoleId());
            List<RoleResourceDO> resourceDOList = roleResourceDao.selectList(query);

            if (Argument.isEmpty(resourceDOList)) {
                continue;
            }
            List<Long> resourceId = CollectionUtils.asList(resourceDOList, RoleResourceDO::getResourceId);
            List<ResourceDO> resources = resourceDao.selectBatchIds(resourceId);

            List<ResourceDO> parentResources = Lists.newArrayList();
            // 递归获取父级资源
            for (ResourceDO resource : resources) {
                findParents(resource, parentResources);
            }
            resourceDOS.addAll(resources);
            resourceDOS.addAll(parentResources);
        }
        return Tuple2.of(resourceDOS, roleNames.substring(0, roleNames.length() - 1));
    }

    /**
     * 校验当前用户是否有改url的访问权限
     *
     * @param userId
     * @param url
     * @return
     */
    @Override
    public Boolean privilegeCheck(Long userId, String url) {
        /**
         * 获取系统所有需要校验权限的url
         * 1.所有权限url为空 返回 true
         * 2.所有权限url不包含校验的url 返回 true
         * 3.需要校验的url包含在所有权限url里并且在当前用户所拥有的权限url里 返回 true
         */
        List<ResourceDO> all = resourceDao.listAll();

        if (all.isEmpty()) {
            return true;
        }
        List<String> allUrl = CollectionUtils.asList(all, ResourceDO::getUrl);

        if (allUrl.isEmpty() || !allUrl.contains(url)) {
            return true;
        }

        // 根据用户id获取他拥有的所有权限资源
        Tuple2<List<ResourceDO>, String> tuple2 = getResourceByUser(userId);
        List<ResourceDO> resources = tuple2.getT0();

        // 该用户不拥有任何权限
        if (resources.isEmpty()) {
            return false;
        }

        // 该用户拥有的所有url权限集合
        Set<String> urlList = CollectionUtils.asSet(resources, ResourceDO::getUrl);
        if (Argument.isEmpty(urlList)) {
            return false;
        }

        // 拥有当前url的权限
        if (urlList.contains(url)) {
            return true;
        }
        return false;
    }

    /**
     * 递归排序
     *
     * @param list
     */
    private void sort(List<ResourceTreeVO> list) {
        for (ResourceTreeVO m : list) {
            if (Argument.isNotEmpty(m.getChildren())) {
                m.setChildren(m.getChildren().stream().sorted(Comparator.comparing(ResourceTreeVO::getSortNo)).collect(Collectors.toList()));
                sort(m.getChildren());
            }
        }
    }

    /**
     * 递归查找父节点
     *
     * @param resource
     * @param list
     */
    private void findParents(ResourceDO resource, List<ResourceDO> list) {
        if (!resource.getParentId().equals(DEFAULT_PARENT_ID)) {
            ResourceDO resourceDO = resourceDao.selectById(resource.getParentId());
            list.add(resourceDO);
            findParents(resourceDO, list);
        }
    }

    /**
     * 构建目录树
     *
     * @param allNodes
     * @return
     */
    public Set<ResourceTreeVO> buildTree(Set<ResourceTreeVO> allNodes) {
        // 根节点
        Set<ResourceTreeVO> root = new HashSet<>();
        allNodes.forEach(node -> {
            if (node.getParentId() == 0) {
                root.add(node);
            }
        });
        root.forEach(node -> {
            findChildren(node, allNodes);
        });
        return root;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    private ResourceTreeVO findChildren(ResourceTreeVO treeNode, Set<ResourceTreeVO> treeNodes) {
        for (ResourceTreeVO it : treeNodes) {
            if (treeNode.getId().equals(it.getParentId())) {
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

    /**
     * 分页获取用户列表
     *
     * @param bo
     * @return
     */
    @Override
    public PageInfo<UserVO> pageList(GetUserPageListBO bo) {
        // 分页
        PageHelper.startPage(bo.getNowPageIndex(), bo.getPageSize());

        List<UserDO> userDOS = userDao.list(UserTransform.INS.transfer(bo));

        PageInfo pageInfo = new PageInfo(userDOS);
        List<UserVO> userVOS = CollectionUtils.convert(userDOS, UserTransform.INS::transfer2);
        pageInfo.setList(userVOS);

        // 查询用户和角色的关联关系
        List<Long> userIds = CollectionUtils.asList(userVOS, UserVO::getId);

        if (Argument.isEmpty(userIds)) {
            return pageInfo;
        }
        OymResult<List<UserRoleDTO>> result = userRoleService.getByUsers(userIds);
        if (result.isFail()) {
            return pageInfo;
        }
        List<UserRoleDTO> userRoleDTOS = result.getData();

        // 转换成user 对应多个关联关系Map
        Map<Long, List<UserRoleDTO>> user2RoleMap = CollectionUtils.group(userRoleDTOS, UserRoleDTO::getUserId);
        userVOS.forEach(userVO -> {
            List<UserRoleDTO> userRoles = user2RoleMap.get(userVO.getId());
            if (Argument.isNotEmpty(userRoles)) {
                List<Long> roleIds = CollectionUtils.asList(userRoles, UserRoleDTO::getRoleId);

                // 设置用户角色ids
                Long[] array = new Long[roleIds.size()];
                userVO.setRoles(roleIds.toArray(array));
            }
        });

        return pageInfo;
    }

    /**
     * 用户保存
     *
     * @param bo
     * @return
     */
    @Override
    public OymResult<Boolean> save(UserSaveBO bo) {
        Integer count = userDao.getCountByName(bo.getUsername());

        // 修改
        if (Argument.isNotNull(bo.getId())) {
            if (count > 1) {
                return OymResult.error(ReturnCode.USER_NAME_EXISTED);
            }
            userRoleService.saveUserRole(new UserRoleSaveBO(bo.getId(), bo.getRoles()));
            return OymResult.success(userDao.updateById(UserTransform.INS.transfer(bo)) > 0);
        }

        // 新增
        if (count > 0) {
            return OymResult.error(ReturnCode.USER_NAME_EXISTED);
        }
        // mybatis plus的这种插入的写法可以返回主键id
        UserDO user = UserTransform.INS.transfer(bo);
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userDao.insert(user);
        userRoleService.saveUserRole(new UserRoleSaveBO(user.getId(), bo.getRoles()));
        return OymResult.success(true);
    }

    /**
     * 用户删除
     *
     * @param id
     * @return
     */
    @Override
    public Boolean delete(Long id) {
        // 删除用户角色关联关系
        QueryWrapper<UserRoleDO> deleteQuery = Wrappers.query();
        deleteQuery.eq("user_id", id);
        userRoleDao.delete(deleteQuery);

        // 删除用户
        return userDao.deleteById(id) > 0;
    }

    /**
     * 用户状态设置
     *
     * @param id
     * @return
     */
    @Override
    public OymResult<Boolean> set(Long id) {
        UserDO user = userDao.selectById(id);

        if (Argument.isNull(user)) {
            return OymResult.error("用户不存在");
        }

        // 设置用户状态
        String status = user.getStatus();
        if (UserStatusEnum.NORMAL.getValue().equals(status)) {
            user.setStatus(UserStatusEnum.DISABLE.getValue());
        } else {
            user.setStatus(UserStatusEnum.NORMAL.getValue());
        }
        return OymResult.success(userDao.updateById(user) > 0);
    }
}
