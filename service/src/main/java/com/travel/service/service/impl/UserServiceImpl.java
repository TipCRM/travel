package com.travel.service.service.impl;

import com.travel.service.bo.LoginBo;
import com.travel.service.bo.UserBasicInfo;
import com.travel.service.entity.Security;
import com.travel.service.entity.User;
import com.travel.service.exception.BizException;
import com.travel.service.exception.UnauthenticatedException;
import com.travel.service.repository.SecurityRepository;
import com.travel.service.repository.UserRepository;
import com.travel.service.service.UserService;
import com.travel.service.utils.Md5SaltTool;
import com.travel.service.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SecurityRepository securityRepository;

    @Override
    public UserBasicInfo login(LoginBo loginBo) {
        switch (loginBo.getLoginType()) {
            case WX:
                return loginByWX(loginBo.getWxOpenId());
            case PHONE_DYNAMIC:
                return loginByPhoneDynamic(loginBo.getPhone(), loginBo.getDynamicCode());
            case PHONE_PASSWORD:
                return loginByPhonePassword(loginBo.getPhone(), loginBo.getPassword());
            default:
                throw new UnauthenticatedException("不支持的登陆方式");

        }
    }

    @Override
    public Boolean cacheUserToken(UserBasicInfo userBasicInfo) {
        String key = buildUserRedisKey(userBasicInfo.getToken());
        return redisUtil.set(key, userBasicInfo.getUserId());
    }

    @Override
    public Long checkLogin(String token) {
        String key = buildUserRedisKey(token);
        return Long.valueOf(redisUtil.get(key).toString());
    }

    private UserBasicInfo loginByPhonePassword(String phoneNumber, String password) {
        User user = userRepository.findByPhone(phoneNumber);
        if (user == null) {
            throw new BizException("手机号或者密码错误");
        }
        Security security = securityRepository.findByUserId(user.getId());
        if (security == null || !Md5SaltTool.validatePassword(password, security.getPassword(), security.getSalt())) {
            throw new BizException("手机号或者密码错误");
        }
        UserBasicInfo loginResBo = new UserBasicInfo();
        loginResBo.setUserId(user.getId());
        loginResBo.setUserName(user.getAlias());
        return loginResBo;
    }

    private UserBasicInfo loginByPhoneDynamic(String phoneNumber, String dynamicCode) {
        throw new UnauthenticatedException("暂不支持手机号动态登陆");
    }

    private UserBasicInfo loginByWX(String wxOpenId) {
        throw new UnauthenticatedException("暂不支持微信登陆");
    }

    private String buildUserRedisKey(String token) {
        return "token:" + token;
    }
}
