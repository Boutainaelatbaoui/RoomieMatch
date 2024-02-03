package com.example.roomiematch.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String telephone;
    private String bio;
    private double budget;
    private int occupation;
    private boolean hasLocal;
    private int gender;
    private LocalDate birthdate;
    private Long currentCityId;
    private Long desiredCityId;

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("token_type")
    private String tokenType;
}