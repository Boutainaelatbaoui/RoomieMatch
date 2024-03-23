package com.example.roomiematch.service.implementations;

import com.example.roomiematch.config.JwtService;
import com.example.roomiematch.enums.TokenType;
import com.example.roomiematch.model.dto.request.AuthenticationRequest;
import com.example.roomiematch.model.dto.request.RefreshTokenRequest;
import com.example.roomiematch.model.dto.request.RegisterRequest;
import com.example.roomiematch.model.dto.response.AuthenticationResponse;
import com.example.roomiematch.model.dto.response.CityResponseDTO;
import com.example.roomiematch.model.dto.response.RefreshTokenResponse;
import com.example.roomiematch.model.entities.City;
import com.example.roomiematch.model.entities.Role;
import com.example.roomiematch.model.entities.User;
import com.example.roomiematch.repository.CityRepository;
import com.example.roomiematch.repository.RoleRepository;
import com.example.roomiematch.repository.TokenRepository;
import com.example.roomiematch.repository.UserRepository;
import com.example.roomiematch.service.implementations.AuthenticationServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void testAuthenticate_SuccessfulAuthentication() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");

        City currentCity = new City();
        currentCity.setId(1L);

        City desiredCity = new City();
        desiredCity.setId(2L);

        user.setCurrentCity(currentCity);
        user.setDesiredCity(desiredCity);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        AuthenticationResponse response = authenticationService.authenticate(request);

        assertNotNull(response);
    }



    @Test
    void testAuthenticate_InvalidCredentials() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("invalid_password");

        assertThrows(EntityNotFoundException.class, () -> {
            authenticationService.authenticate(request);
        });
    }

    @Test
    void testAuthenticate_UserNotFound() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("nonexistent@example.com");
        request.setPassword("password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            authenticationService.authenticate(request);
        });
    }



}
