package com.tc.core.utils;

import com.alibaba.fastjson.JSON;
import com.tc.core.web.responce.AdminVO;
import com.tc.core.web.responce.UserVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author wy740
 */
@Slf4j
public class JwtUtils {

    /**
     * 秘钥
     */
    private final static String SECRET_KEY = "a4731a8abf714b5f94511448fbb0f37a";

    /**
     * 过期时间:1天
     */
    public final static Integer EXPIRES_IN = 3600 * 24 * 30;

    /**
     * 生成Token签名
     * @return 签名字符串
     */
    public static String generateToken(AdminVO admin) {
        Claims claims = Jwts.claims();
        claims.put("admin", JSON.toJSONString(admin));


        claims.setIssuedAt(new Date());
        Date expiration = DateUtils.addSeconds(new Date(), EXPIRES_IN);
        claims.setExpiration(expiration);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public static String generateToken(UserVO user) {
        Claims claims = Jwts.claims();
        claims.put("user", JSON.toJSONString(user));


        claims.setIssuedAt(new Date());
        Date expiration = DateUtils.addSeconds(new Date(), EXPIRES_IN);
        claims.setExpiration(expiration);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    /**
     * 获取签名信息
     *
     * @param token
     */
    public static Claims getClaimByToken(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断Token是否过期
     *
     * @param expiration
     * @return true 过期
     */
    public static boolean isTokenExpired(Date expiration) {
        return expiration.before(DateUtils.now());
    }

    /**
     * 验证token是否正确且未过期
     *
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        Claims claims = getClaimByToken(token);
        return claims != null && !isTokenExpired(claims.getExpiration());
    }
}
