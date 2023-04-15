package com.simo.emos.wx.config.securityConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @Description
 * @Author simo
 * @Date 2023/1/3 15:14
 * @Version 1.0
 **/

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true,securedEnabled=true, jsr250Enabled=true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenAuthenticationProvider tokenAuthenticationProvider;
//    @Autowired
//    protected void initialize(AuthenticationManagerBuilder builder) throws Exception {
//        builder.authenticationProvider(new TokenAuthenticationProvider());
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new TokenAuthenticationFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .mvcMatchers("/test")
                .hasRole("ROOT")
                .mvcMatchers("/user/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/user/register");
        web.ignoring().mvcMatchers("/user/login");
//        web.ignoring().mvcMatchers("/checkin/validCanCheckIn");
        web.ignoring().mvcMatchers("/swagger-ui/index.html");
        web.ignoring().mvcMatchers("/v2/api-docs");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(tokenAuthenticationProvider);
    }
}
