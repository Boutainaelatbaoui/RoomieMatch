package com.example.roomiematch.service.implementations;

import com.example.roomiematch.config.JwtService;
import com.example.roomiematch.enums.RoleName;
import com.example.roomiematch.enums.TokenType;
import com.example.roomiematch.model.dto.request.AuthenticationRequest;
import com.example.roomiematch.model.dto.request.RefreshTokenRequest;
import com.example.roomiematch.model.dto.request.RegisterRequest;
import com.example.roomiematch.model.dto.response.AuthenticationResponse;
import com.example.roomiematch.model.dto.response.CityResponseDTO;
import com.example.roomiematch.model.dto.response.RefreshTokenResponse;
import com.example.roomiematch.model.entities.City;
import com.example.roomiematch.model.entities.Role;
import com.example.roomiematch.model.entities.Token;
import com.example.roomiematch.model.entities.User;
import com.example.roomiematch.repository.CityRepository;
import com.example.roomiematch.repository.RoleRepository;
import com.example.roomiematch.repository.TokenRepository;
import com.example.roomiematch.repository.UserRepository;
import com.example.roomiematch.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CityRepository cityRepository;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email is already registered");
        }

        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new EntityNotFoundException("Role not found in the database"));

        City currentCity = cityRepository.findById(request.getCurrentCityId())
                .orElseThrow(() -> new EntityNotFoundException("Current City not found"));

        City desiredCity = cityRepository.findById(request.getDesiredCityId())
                .orElseThrow(() -> new EntityNotFoundException("Desired City not found"));

        LocalDate currentDate = LocalDate.now();
        int age = Period.between(request.getBirthdate(), currentDate).getYears();

        if (age < 18) {
            throw new ValidationException("You must be 18 years or older to register");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .telephone(request.getTelephone())
                .bio(request.getBio())
                .budget(request.getBudget())
                .occupation(request.getOccupation())
                .gender(request.getGender())
                .birthdate(request.getBirthdate())
                .currentCity(currentCity)
                .desiredCity(desiredCity)
                .build();

        userRepository.save(user);
        return authenticationResponse(user);
    }


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return authenticationResponse(user);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUserId(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest, HttpServletResponse response) throws IOException {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        if (refreshToken != null) {
            String userEmail = jwtService.extractUserName(refreshToken);

            if (userEmail != null) {
                User user = userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                if (jwtService.isTokenValid(refreshToken, user)) {
                    var accessToken = jwtService.generateToken(user);
                    revokeAllUserTokens(user);
                    saveUserToken(user, accessToken);
                    return RefreshTokenResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                }
            }
        }
        throw new AuthenticationException("Refresh token validation failed");
    }



    private AuthenticationResponse authenticationResponse(User user){
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        CityResponseDTO currentCityDTO = CityResponseDTO.fromEntity(user.getCurrentCity());
        CityResponseDTO desiredCityDTO = CityResponseDTO.fromEntity(user.getDesiredCity());

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .tokenType(TokenType.BEARER.name())
                .id(user.getId())
                .build();
    }
}

