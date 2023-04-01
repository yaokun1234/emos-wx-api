package com.simo.emos.wx.config.securityConfig;

import com.simo.emos.wx.dao.entity.User;
import com.simo.emos.wx.service.UserService;
import com.simo.emos.wx.util.TokenUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;

    public TokenAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        if (authentication.isAuthenticated()) {
            return authentication;
        }

        // 从 TokenAuthentication 中获取 token
        String token = authentication.getCredentials().toString();
        if (!StringUtils.hasText(token)) {
            return null;
        }
        String openId = TokenUtil.verifyToken(token);


        User user = userService.findByOpenId(openId);
        Set<String> permissions = userService.searchUserPermissions(openId);
        List<SimpleGrantedAuthority> authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        // 返回新的认证信息，带上 token 和反查出的用户信息
        Authentication auth = new PreAuthenticatedAuthenticationToken(user, token, authorities);
        auth.setAuthenticated(true);
        return auth;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (TokenAuthentication.class.isAssignableFrom(aClass));
    }
}