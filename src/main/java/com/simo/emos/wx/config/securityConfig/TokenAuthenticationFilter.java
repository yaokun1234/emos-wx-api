package com.simo.emos.wx.config.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

//        SecurityContext context = SecurityContextHolder.getContext();
//        if (context.getAuthentication() != null && context.getAuthentication().isAuthenticated()) {
//
//        } else {

//            String token = req.getHeader("token");
//            Authentication auth = new TokenAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//            req.setAttribute("me.lotabout.springsecurityexample.security.TokenAuthenticationFilter.FILTERED", true); //③

//        }

//        fc.doFilter(req, res); //④


        String token = httpServletRequest.getHeader("token");

        // 如果没有token，跳过该过滤器
        if (!StringUtils.isEmpty(token)) {
            Authentication auth = new TokenAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}