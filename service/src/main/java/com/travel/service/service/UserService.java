package com.travel.service.service;

import com.travel.service.bo.LoginBo;
import com.travel.service.bo.UserBasicInfo;

public interface UserService {
    UserBasicInfo login(LoginBo loginBo);

    Boolean cacheUserToken(UserBasicInfo userBasicInfo);

    Long checkLogin(String token);
}
