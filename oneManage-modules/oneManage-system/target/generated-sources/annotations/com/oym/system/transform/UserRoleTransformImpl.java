package com.oym.system.transform;

import com.oym.system.domain.dataobject.UserRoleDO;
import com.oym.system.domain.dto.UserRoleDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-05T15:16:47+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
@Component
public class UserRoleTransformImpl implements UserRoleTransform {

    @Override
    public UserRoleDTO transfer(UserRoleDO serRoleDO) {
        if ( serRoleDO == null ) {
            return null;
        }

        UserRoleDTO userRoleDTO = new UserRoleDTO();

        userRoleDTO.setUserId( serRoleDO.getUserId() );
        userRoleDTO.setRoleId( serRoleDO.getRoleId() );

        return userRoleDTO;
    }
}