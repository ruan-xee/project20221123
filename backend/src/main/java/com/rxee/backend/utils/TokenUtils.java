package com.rxee.backend.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class TokenUtils {
    /**
     * 生成token
     * @return
     */
    public static String getToken(String userId, String sign){
        return JWT.create().withAudience(userId)   //将userId作为载荷保存到token里
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))  //设置2小时后token过期
                .sign(Algorithm.HMAC256(sign)); //以password作为密钥
    }
}
