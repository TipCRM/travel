package com.travel.api.controller;

import com.travel.api.annotation.LoginIgnore;
import com.travel.api.utils.JsonEntity;
import com.travel.api.utils.JwtHelper;
import com.travel.api.utils.ResponseHelper;
import com.travel.service.bo.LoginBo;
import com.travel.service.bo.UserBasicInfo;
import com.travel.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("/login")
    @LoginIgnore
    public JsonEntity<String> login(@RequestBody LoginBo loginBo) {
        UserBasicInfo loginRes = userService.login(loginBo);
        String jwtToken = jwtHelper.createJwtToken(loginRes.getUserId());
        loginRes.setToken(jwtToken);
        userService.cacheUserToken(loginRes);
        return ResponseHelper.createInstance(jwtToken);
    }

}
