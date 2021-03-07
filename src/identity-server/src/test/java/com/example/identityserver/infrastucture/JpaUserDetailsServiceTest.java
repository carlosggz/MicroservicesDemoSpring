package com.example.identityserver.infrastucture;

import com.example.identityserver.objectmothers.UserObjectMother;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaUserDetailsServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    JpaUserDetailsService userDetailsService;

    @Test
    public void invalidUserThrowsNotFoundException() {

        val userName = UserObjectMother.getRandomUsername();
        when(userRepository.findById(userName)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(userName));
    }

    @Test
    public void validUserReturnsUserDetails() {

        val user = UserObjectMother.getRandomUser();
        when(userRepository.findById(user.getUserName())).thenReturn(Optional.of(user));

        val userDetails = userDetailsService.loadUserByUsername(user.getUserName());

        assertEquals(user.getUserName(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(userDetails.getAuthorities().size(), 1);
        assertTrue(userDetails.getAuthorities().contains(user.getRole()));
    }
}