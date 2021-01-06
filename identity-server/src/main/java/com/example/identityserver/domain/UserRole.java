package com.example.identityserver.domain;

import com.example.identityserver.config.Constants;
import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    USER(Constants.USER_ROLE),
    ADMIN(Constants.ADMIN_ROLE);

    private String value;

    UserRole(String value){
        this.value = value;
    }

    @Override
    public String getAuthority() {
        return this.value;
    }
}
