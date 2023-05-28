package com.simo.emos.wx;

import cn.hutool.core.util.StrUtil;
import com.simo.emos.wx.dao.entity.SysConfig;
import com.simo.emos.wx.dao.entity.constant.SystemConstants;
import com.simo.emos.wx.dao.repository.SysConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.List;

@SpringBootApplication
@EnableSwagger2
@Slf4j
public class EmosWxApiApplication {

    @Autowired
    private SysConfigRepository sysConfigRepository;

    @Autowired
    private SystemConstants systemConstants;

    public static void main(String[] args) {
        SpringApplication.run(EmosWxApiApplication.class, args);
    }

    @PostConstruct
    public void init(){
        List<SysConfig> configs = sysConfigRepository.findAll();
        configs.forEach(config->{
            String paramKey = config.getParamKey();
            String key = StrUtil.toCamelCase(paramKey);
            String paramValue = config.getParamValue();
            try {
                Field field = systemConstants.getClass().getDeclaredField(key);
                field.set(systemConstants,paramValue);
            } catch (Exception e) {
                log.error("获取系统常量异常",e);
            }
        });
    }
}
