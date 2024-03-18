package com.example.roomiematch.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Family name cannot be blank")
    @Size(min = 2, max = 50, message = "Family name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "The Password must have at least 6 characters")
    private String password;

    private Long role_id;

    @NotBlank(message = "Telephone cannot be blank")
    @Size(min = 10, max = 15, message = "Telephone must be between 10 and 15 characters")
    private String telephone;

    @NotBlank(message = "Bio is required")
    @Size(max = 255, message = "Bio must be less than 255 characters")
    private String bio;

    @NotNull(message = "Budget cannot be null")
    @DecimalMin("10.0")
    private double budget;

    @NotNull(message = "Gender cannot be null")
    private int occupation;

    @NotNull(message = "Gender cannot be null")
    private int gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    //private String profilePicture;

    @NotNull(message = "Current City ID cannot be null")
    private Long currentCityId;

    @NotNull(message = "Desired City ID cannot be null")
    private Long desiredCityId;
}
