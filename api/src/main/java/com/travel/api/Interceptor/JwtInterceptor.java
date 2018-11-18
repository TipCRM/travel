package com.travel.api.Interceptor;

import com.travel.api.annotation.LoginIgnore;
import com.travel.api.utils.JwtHelper;
import com.travel.service.constants.CommonConstants;
import com.travel.service.exception.UnauthenticatedException;
import com.travel.service.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserService userService;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.getAnnotation(LoginIgnore.class) == null) {
            String accessToken = request.getHeader(CommonConstants.HEADER_TOKEN_NAME);
            if (null == accessToken) {
                throw new UnauthenticatedException("未登录");
            }
            Claims claims = jwtHelper.parseJWT(accessToken);
            Long userId = Long.valueOf(claims.getId());
            Long userIdInRedis = userService.checkLogin(accessToken);
            if ( userIdInRedis != null && userIdInRedis.equals(userId)) {
                request.setAttribute(CommonConstants.REQUEST_ATTRIBUTE_CURRENT_USER, userId);
                return true;
            }
        }
        return true;
    }
}
