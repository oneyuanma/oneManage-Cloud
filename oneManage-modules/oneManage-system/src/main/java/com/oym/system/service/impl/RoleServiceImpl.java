package com.oym.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.cons.ReturnCode;
import com.oym.commons.utils.Argument;
import com.oym.commons.utils.CollectionUtils;
import com.oym.system.ctrl.vo.RoleSelectVO;
import com.oym.system.ctrl.vo.RoleVO;
import com.oym.system.dao.RoleDao;
import com.oym.system.dao.RoleResourceDao;
import com.oym.system.domain.bo.GetRoleListBO;
import com.oym.system.domain.bo.GetRolePageListBO;
import com.oym.system.domain.bo.RoleSaveBO;
import com.oym.system.domain.dataobject.RoleDO;
import com.oym.system.service.RoleService;
import com.oym.system.transform.RoleTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统角色service实现
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleDO> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleResourceDao roleResourceDao;

    /**
     * 分页获取角色列表
     * @param bo
     * @return
     */
    @Override
    public PageInfo<RoleVO> pageList(GetRolePageListBO bo) {
        // 分页
        PageHelper.startPage(bo.getNowPageIndex(), bo.getPageSize());

        List<RoleDO> roleDOS = roleDao.list(RoleTransform.INS.transfer(bo));

        PageInfo pageInfo = new PageInfo(roleDOS);

        pageInfo.setList(CollectionUtils.convert(roleDOS, RoleTransform.INS::transfer));

        return pageInfo;
    }

    /**
     * 角色列表
     * @param bo
     * @return
     */
    @Override
    public List<RoleVO> list(GetRoleListBO bo) {
        List<RoleDO> roleDOS = roleDao.list(RoleTransform.INS.transfer(bo));
        return CollectionUtils.convert(roleDOS, RoleTransform.INS::transfer);
    }

    /**
     * 角色下拉列表项
     * @return
     */
    @Override
    public List<RoleSelectVO> selectOptions() {
        List<RoleSelectVO> roleSelectVOS = Lists.newArrayList();

        List<RoleDO> roleDOS = roleDao.list(new RoleDO());
        if (Argument.isEmpty(roleDOS)) {
            return roleSelectVOS;
        }

        for (RoleDO role : roleDOS) {
            RoleSelectVO vo = new RoleSelectVO();
            vo.setValue(role.getId());
            vo.setLabel(role.getName());
            roleSelectVOS.add(vo);
        }
        return roleSelectVOS;
    }

    /**
     * 角色保存
     * @param bo
     * @return
     */
    @Override
    public OymResult<Boolean> save(RoleSaveBO bo) {

        Integer countByCode = roleDao.getCountByName(bo.getCode(), null);
        Integer countByName = roleDao.getCountByName(null, bo.getName());

        if (Argument.isNotNull(bo.getId())) {
            if (countByCode > 1) {
                return OymResult.error(ReturnCode.ROLE_CODE_EXISTED);
            }
            if (countByName > 1) {
                return OymResult.error(ReturnCode.ROLE_NAME_EXISTED);
            }
            return OymResult.success(roleDao.updateById(RoleTransform.INS.transfer(bo)) > 0);
        }

        if (countByCode > 1) {
            return OymResult.error(ReturnCode.ROLE_NAME_EXISTED);
        }
        if (countByName > 1) {
            return OymResult.error("角色名称已存在");
        }

        return OymResult.success(roleDao.insert(RoleTransform.INS.transfer(bo)) > 0);
    }

    /**
     * 角色删除
     * @param id
     * @return
     */
    @Override
    public Boolean delete(Long id) {
        return roleDao.deleteById(id) > 0;
    }

}
