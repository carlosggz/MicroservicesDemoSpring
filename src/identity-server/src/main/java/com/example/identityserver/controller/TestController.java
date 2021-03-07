package com.example.identityserver.controller;

import com.example.identityserver.domain.UserRole;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.identityserver.config.Constants;
import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/test")
public class TestController {

    @PreAuthorize("hasAuthority('" + Constants.USER_ROLE + "')")
    @GetMapping("/user")
    public String isUser(){
        return getUserName() + " is an user";
    }

    @PreAuthorize("hasAuthority('" + Constants.ADMIN_ROLE + "')")
    @GetMapping("/admin")
    public String isAdmin(){
        return getUserName() + " is an admin";
    }

    @GetMapping("/info")
    public String info(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("N/A");
    }

    private String getUserName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getName();
    }
}
