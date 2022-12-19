package com.rxee.backend.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rxee.backend.entity.User;
import com.rxee.backend.service.IUserService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

public class TokenUtils {
    private static IUserService staticUserService;

    @Resource
    private IUserService userService;

    @PostConstruct
    public void setUserService(){
        staticUserService = userService;
    }

    /**
     * 生成token
     * @return
     */
    public static String getToken(String userId, String sign){
        return JWT.create().withAudience(userId)   //将userId作为载荷保存到token里
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))  //设置2小时后token过期
                .sign(Algorithm.HMAC256(sign)); //以password作为密钥
    }

    public static User getCurrentUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (Objects.isNull(token) || "".equals(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(userId));
            }
        } catch (Exception e){
            return null;
        }
        return null;
    }
}
