package com.oym.system.transform;

import com.oym.commons.user.UserLoginInfo;
import com.oym.system.ctrl.request.GetUserPageListRequest;
import com.oym.system.ctrl.request.UserSaveRequest;
import com.oym.system.ctrl.vo.UserInfoVO;
import com.oym.system.ctrl.vo.UserVO;
import com.oym.system.domain.bo.GetUserPageListBO;
import com.oym.system.domain.bo.UserSaveBO;
import com.oym.system.domain.dataobject.UserDO;
import java.util.Arrays;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-05T15:16:47+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
@Component
public class UserTransformImpl implements UserTransform {

    @Override
    public UserLoginInfo transfer(UserDO userDO) {
        if ( userDO == null ) {
            return null;
        }

        UserLoginInfo userLoginInfo = new UserLoginInfo();

        userLoginInfo.setId( userDO.getId() );
        userLoginInfo.setUsername( userDO.getUsername() );
        userLoginInfo.setPassword( userDO.getPassword() );
        userLoginInfo.setNickName( userDO.getNickName() );
        userLoginInfo.setEmail( userDO.getEmail() );
        userLoginInfo.setPhone( userDO.getPhone() );
        userLoginInfo.setSex( userDO.getSex() );

        return userLoginInfo;
    }

    @Override
    public UserInfoVO transfer1(UserDO userDO) {
        if ( userDO == null ) {
            return null;
        }

        UserInfoVO userInfoVO = new UserInfoVO();

        userInfoVO.setUsername( userDO.getUsername() );
        userInfoVO.setNickName( userDO.getNickName() );
        userInfoVO.setEmail( userDO.getEmail() );
        userInfoVO.setPhone( userDO.getPhone() );
        userInfoVO.setSex( userDO.getSex() );
        userInfoVO.setLastLoginTime( userDO.getLastLoginTime() );
        userInfoVO.setLastLoginIp( userDO.getLastLoginIp() );

        return userInfoVO;
    }

    @Override
    public UserVO transfer2(UserDO userDO) {
        if ( userDO == null ) {
            return null;
        }

        UserVO userVO = new UserVO();

        userVO.setId( userDO.getId() );
        userVO.setUsername( userDO.getUsername() );
        userVO.setNickName( userDO.getNickName() );
        userVO.setPassword( userDO.getPassword() );
        userVO.setEmail( userDO.getEmail() );
        userVO.setPhone( userDO.getPhone() );
        userVO.setSex( userDO.getSex() );
        userVO.setStatus( userDO.getStatus() );

        return userVO;
    }

    @Override
    public GetUserPageListBO transfer(GetUserPageListRequest request) {
        if ( request == null ) {
            return null;
        }

        GetUserPageListBO getUserPageListBO = new GetUserPageListBO();

        getUserPageListBO.setNowPageIndex( request.getNowPageIndex() );
        getUserPageListBO.setPageSize( request.getPageSize() );
        getUserPageListBO.setUsername( request.getUsername() );
        getUserPageListBO.setNickName( request.getNickName() );

        return getUserPageListBO;
    }

    @Override
    public UserDO transfer(GetUserPageListBO bo) {
        if ( bo == null ) {
            return null;
        }

        UserDO userDO = new UserDO();

        userDO.setUsername( bo.getUsername() );
        userDO.setNickName( bo.getNickName() );

        return userDO;
    }

    @Override
    public UserSaveBO transfer(UserSaveRequest request) {
        if ( request == null ) {
            return null;
        }

        UserSaveBO userSaveBO = new UserSaveBO();

        userSaveBO.setId( request.getId() );
        userSaveBO.setUsername( request.getUsername() );
        userSaveBO.setNickName( request.getNickName() );
        userSaveBO.setPassword( request.getPassword() );
        userSaveBO.setEmail( request.getEmail() );
        userSaveBO.setPhone( request.getPhone() );
        userSaveBO.setSex( request.getSex() );
        Long[] roles = request.getRoles();
        if ( roles != null ) {
            userSaveBO.setRoles( Arrays.copyOf( roles, roles.length ) );
        }

        return userSaveBO;
    }

    @Override
    public UserDO transfer(UserSaveBO bo) {
        if ( bo == null ) {
            return null;
        }

        UserDO userDO = new UserDO();

        userDO.setId( bo.getId() );
        userDO.setUsername( bo.getUsername() );
        userDO.setNickName( bo.getNickName() );
        userDO.setPassword( bo.getPassword() );
        userDO.setEmail( bo.getEmail() );
        userDO.setPhone( bo.getPhone() );
        userDO.setSex( bo.getSex() );

        return userDO;
    }
}
