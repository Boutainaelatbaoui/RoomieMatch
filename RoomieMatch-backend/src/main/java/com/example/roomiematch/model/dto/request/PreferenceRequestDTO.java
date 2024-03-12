package com.example.roomiematch.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceRequestDTO {
    @NotNull(message = "userId is required")
    private Long userId;
    @NotNull(message = "smoking is required")
    private boolean smoking;
    @NotNull(message = "pets is required")
    private boolean pets;
    @NotNull(message = "visitors is required")
    private boolean visitors;
    @NotNull(message = "partying is required")
    private boolean partying;
    @NotNull(message = "sharingBedroom is required")
    private boolean sharingBedroom;
    @NotNull(message = "hasApartment is required")
    private boolean hasApartment;
}


