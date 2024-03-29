package com.simo.emos.wx.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.simo.emos.wx.config.exception.ConditionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class TokenUtil {



    private static int expire;

    @Value(value = "${token.expire}")
    public void setExpire(int expires) {
        expire = expires;
    }

    private static final String ISSUER = "签发者";
    public static String generateToken(String userId){
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,expire);
        Date time = calendar.getTime();
        return JWT.create().withKeyId(userId)
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);

    }

    public static String verifyToken(String token){

        try {
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getKeyId();
        } catch (TokenExpiredException e) {
            throw new ConditionException("555","Token过期异常");
        } catch (Exception e){
            throw new ConditionException("非法用户Token");
        }
    }

    public static String generateRefreshToken(String userId) {
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,7);
        return JWT.create().withKeyId(userId)
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }

    public static void main(String[] args) {
//        String encode = URLEncoder.encode("轻语");
//        System.out.println("111111111111111:"+encode);
        Integer i = null;
        if(i == 0){

        }

    }


}
