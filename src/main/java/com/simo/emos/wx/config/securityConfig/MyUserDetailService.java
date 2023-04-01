package com.simo.emos.wx.config.securityConfig;

import com.simo.emos.wx.dao.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Description
 * @Author simo
 * @Date 2023/3/9 01:25
 * @Version 1.0
 **/


public class MyUserDetailService implements UserDetailsService {
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String openid) throws UsernameNotFoundException {
//        User user = userRepository.findByOpenId(openid);


        return null;
    }
}
