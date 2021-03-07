package com.example.identityserver.services;

import org.springframework.stereotype.Service;

public interface UsersService {

    public String authenticateUser(String userName, String password) throws Exception;
}
