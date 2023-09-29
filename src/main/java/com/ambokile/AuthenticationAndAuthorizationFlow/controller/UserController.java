package com.ambokile.AuthenticationAndAuthorizationFlow.controller;

import com.ambokile.AuthenticationAndAuthorizationFlow.entity.AuthRequest;
import com.ambokile.AuthenticationAndAuthorizationFlow.entity.UserInfo;
import com.ambokile.AuthenticationAndAuthorizationFlow.jwt.JwtService;
import com.ambokile.AuthenticationAndAuthorizationFlow.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String Welcome(){
        return "Welcome endpoint not secured";
    }
    @PostMapping("/addNewUser")
    public String AddNewUser(@RequestBody UserInfo userInfo){
        return userInfoService.addUser(userInfo);

    }
    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String UserProfile(){
        return "Welcome to user profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public String AuthenticateAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));

        if (authentication.isAuthenticated()){
            return jwtService.generationToken(authRequest.getUsername());
        }else {
            throw new UsernameNotFoundException("user not found");
        }

    }


}
