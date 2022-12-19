package com.rxee.backend.config.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rxee.backend.common.Constants;
import com.rxee.backend.entity.User;
import com.rxee.backend.exception.ServiceException;
import com.rxee.backend.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class JWTInterceptor implements HandlerInterceptor {
    @Resource
    IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");  //获取前端发送过来的token
        //如果不是映射到方法，直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //执行认证
        if (Objects.isNull(token) || "".equals(token)){
            throw new ServiceException(Constants.CODE_401, "无token，请重新登录！");
        }
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j){
            throw new ServiceException(Constants.CODE_401, "token验证失败！");
        }
        User user = userService.getById(userId);
        if (Objects.isNull(user)){
            throw new ServiceException(Constants.CODE_401, "用户不存在，请重新登录！");
        }
        //验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException j){
            throw new ServiceException(Constants.CODE_401, "token验证失败！");
        }
        return true;
    }
}
