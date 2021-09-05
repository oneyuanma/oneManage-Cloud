package com.oym.system.transform;

import com.oym.system.ctrl.request.GetRoleListRequest;
import com.oym.system.ctrl.request.GetRolePageListRequest;
import com.oym.system.ctrl.request.RoleResourceSaveRequest;
import com.oym.system.ctrl.request.RoleSaveRequest;
import com.oym.system.ctrl.vo.RoleVO;
import com.oym.system.domain.bo.GetRoleListBO;
import com.oym.system.domain.bo.GetRolePageListBO;
import com.oym.system.domain.bo.RoleResourceSaveBO;
import com.oym.system.domain.bo.RoleSaveBO;
import com.oym.system.domain.dataobject.RoleDO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-05T15:16:47+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
@Component
public class RoleTransformImpl implements RoleTransform {

    @Override
    public RoleDO transfer(GetRolePageListBO bo) {
        if ( bo == null ) {
            return null;
        }

        RoleDO roleDO = new RoleDO();

        roleDO.setCode( bo.getCode() );
        roleDO.setName( bo.getName() );

        return roleDO;
    }

    @Override
    public RoleVO transfer(RoleDO roleDO) {
        if ( roleDO == null ) {
            return null;
        }

        RoleVO roleVO = new RoleVO();

        roleVO.setId( roleDO.getId() );
        roleVO.setCode( roleDO.getCode() );
        roleVO.setName( roleDO.getName() );
        roleVO.setDescription( roleDO.getDescription() );

        return roleVO;
    }

    @Override
    public RoleDO transfer(RoleSaveBO bo) {
        if ( bo == null ) {
            return null;
        }

        RoleDO roleDO = new RoleDO();

        roleDO.setId( bo.getId() );
        roleDO.setCode( bo.getCode() );
        roleDO.setName( bo.getName() );
        roleDO.setDescription( bo.getDescription() );

        return roleDO;
    }

    @Override
    public GetRolePageListBO transfer(GetRolePageListRequest request) {
        if ( request == null ) {
            return null;
        }

        GetRolePageListBO getRolePageListBO = new GetRolePageListBO();

        getRolePageListBO.setNowPageIndex( request.getNowPageIndex() );
        getRolePageListBO.setPageSize( request.getPageSize() );
        getRolePageListBO.setCode( request.getCode() );
        getRolePageListBO.setName( request.getName() );

        return getRolePageListBO;
    }

    @Override
    public RoleSaveBO transfer(RoleSaveRequest request) {
        if ( request == null ) {
            return null;
        }

        RoleSaveBO roleSaveBO = new RoleSaveBO();

        roleSaveBO.setId( request.getId() );
        roleSaveBO.setCode( request.getCode() );
        roleSaveBO.setName( request.getName() );
        roleSaveBO.setDescription( request.getDescription() );

        return roleSaveBO;
    }

    @Override
    public GetRoleListBO transfer(GetRoleListRequest request) {
        if ( request == null ) {
            return null;
        }

        GetRoleListBO getRoleListBO = new GetRoleListBO();

        getRoleListBO.setCode( request.getCode() );
        getRoleListBO.setName( request.getName() );

        return getRoleListBO;
    }

    @Override
    public RoleDO transfer(GetRoleListBO bo) {
        if ( bo == null ) {
            return null;
        }

        RoleDO roleDO = new RoleDO();

        roleDO.setCode( bo.getCode() );
        roleDO.setName( bo.getName() );

        return roleDO;
    }

    @Override
    public RoleResourceSaveBO transfer(RoleResourceSaveRequest request) {
        if ( request == null ) {
            return null;
        }

        RoleResourceSaveBO roleResourceSaveBO = new RoleResourceSaveBO();

        roleResourceSaveBO.setNowPageIndex( request.getNowPageIndex() );
        roleResourceSaveBO.setPageSize( request.getPageSize() );
        roleResourceSaveBO.setRoleId( request.getRoleId() );
        roleResourceSaveBO.setResourceIds( request.getResourceIds() );

        return roleResourceSaveBO;
    }
}
