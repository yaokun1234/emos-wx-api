package com.simo.emos.wx.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.simo.emos.wx.config.exception.ConditionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Description
 * @Author simo
 * @Date 2023/2/21 23:09
 * @Version 1.0
 **/

@Service
public class CommonService {

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.app-secret}")
    private String appSecret;

    @Value("${wx.get-openid-url}")
    private String getOpenidUrl;


    public String getOpenid(String code){
        HashMap<String, Object> params = new HashMap<>();
        params.put("appid",appid);
        params.put("secret",appSecret);
        params.put("js_code",code);
        params.put("grant_type","authorization_code");
        HttpResponse httpResponse = HttpRequest.get(getOpenidUrl).form(params).execute();
        if(httpResponse.getStatus() != HttpStatus.HTTP_OK){
            String exceptionMess = httpResponse.body();
            throw new ConditionException("获取用户OpenId异常，Message:"+exceptionMess);
        }
        String bodyJson = httpResponse.body();
        String string = httpResponse.toString();
        JSONObject jsonObject = JSONUtil.parseObj(bodyJson);

        if(jsonObject.containsKey("openid")){
            return jsonObject.get("openid").toString();
        }else{
            throw new ConditionException("获取用户OpenId异常，Message:"+jsonObject.toString());
        }
    }
}
