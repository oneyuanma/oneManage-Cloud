package com.oym.system.transform;

import com.oym.commons.user.UserLoginInfo;
import com.oym.system.domain.bo.GetUserPageListBO;
import com.oym.system.domain.bo.UserSaveBO;
import com.oym.system.domain.dataobject.UserDO;
import com.oym.system.ctrl.request.GetUserPageListRequest;
import com.oym.system.ctrl.request.UserSaveRequest;
import com.oym.system.ctrl.vo.UserInfoVO;
import com.oym.system.ctrl.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统用户实体转换器
 *
 * @author oneyuanma
 * @date 2020/06/23
 */
@Mapper(componentModel = "spring")
public interface UserTransform {

    UserTransform INS = Mappers.getMapper(UserTransform.class);

    UserLoginInfo transfer(UserDO userDO);

    UserInfoVO transfer1(UserDO userDO);

    UserVO transfer2(UserDO userDO);

    GetUserPageListBO transfer(GetUserPageListRequest request);

    UserDO transfer(GetUserPageListBO bo);

    UserSaveBO transfer(UserSaveRequest request);

    UserDO transfer(UserSaveBO bo);
}
