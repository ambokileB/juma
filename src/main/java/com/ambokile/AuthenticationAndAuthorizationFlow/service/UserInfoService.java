package com.ambokile.AuthenticationAndAuthorizationFlow.service;

import com.ambokile.AuthenticationAndAuthorizationFlow.entity.UserInfo;
import com.ambokile.AuthenticationAndAuthorizationFlow.jwt.UserInfoDetails;
import com.ambokile.AuthenticationAndAuthorizationFlow.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Encoder;
import java.util.Map;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetails = userInfoRepository.findByUsername(username);
        return userDetails.map(UserInfoDetails::new)
                .orElseThrow(()->new UsernameNotFoundException("user not found "+ username));
    }
    public String addUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "user registered successfully";
    }
}
