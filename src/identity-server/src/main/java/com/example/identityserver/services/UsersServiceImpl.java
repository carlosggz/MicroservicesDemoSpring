package com.example.identityserver.services;

import com.example.identityserver.infrastucture.JpaUserDetailsService;
import com.example.shared.security.JwtTokenUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JpaUserDetailsService userDetailsService;

    @Override
    public String authenticateUser(@NonNull String userName, @NonNull String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        val userDetails = userDetailsService.loadUserByUsername(userName);

        return jwtTokenUtil.generateToken(userDetails);
    }

}
