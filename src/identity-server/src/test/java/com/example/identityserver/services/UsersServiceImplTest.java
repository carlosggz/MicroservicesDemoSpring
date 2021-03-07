package com.example.identityserver.services;

import com.example.identityserver.infrastucture.JpaUserDetailsService;
import com.example.identityserver.objectmothers.UserObjectMother;
import com.example.shared.security.JwtTokenUtil;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtTokenUtil jwtTokenUtil;

    @Mock
    JpaUserDetailsService userDetailsService;

    @InjectMocks
    UsersServiceImpl usersService;

    @Test
    public void disabledUserThrowsException() {
        when(authenticationManager.authenticate(any())).thenThrow(new DisabledException("message"));

        assertThrows(Exception.class, () -> usersService.authenticateUser(UserObjectMother.getRandomUsername(), UserObjectMother.getRandomPassword()));
    }

    @Test
    public void invalidCredentialsThrowsException() {
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("message"));

        assertThrows(Exception.class, () -> usersService.authenticateUser(UserObjectMother.getRandomUsername(), UserObjectMother.getRandomPassword()));
    }

    @Test
    public void validUserReturnsToken() throws Exception {
        val expectedToken = "123";
        val user = UserObjectMother.getRandomUser();
        val userDetails = new org.springframework.security.core.userdetails.User(
                user.getUserName(), user.getPassword(), List.of(user.getRole()));
        when(userDetailsService.loadUserByUsername(user.getUserName())).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn(expectedToken);

        val result = usersService.authenticateUser(user.getUserName(), UserObjectMother.getRandomPassword());

        assertEquals(expectedToken, result);
    }
}