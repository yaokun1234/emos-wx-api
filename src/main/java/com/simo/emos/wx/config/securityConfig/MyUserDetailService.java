package com.simo.emos.wx.config.securityConfig;

import com.simo.emos.wx.config.exception.ConditionException;
import com.simo.emos.wx.dao.entity.User;
import com.simo.emos.wx.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author simo
 * @Date 2023/3/9 01:25
 * @Version 1.0
 **/


@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new ConditionException("用户不存在");
        }
    }
}
