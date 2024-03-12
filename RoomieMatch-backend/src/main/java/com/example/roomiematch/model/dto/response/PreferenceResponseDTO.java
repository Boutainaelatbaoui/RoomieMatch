package com.example.roomiematch.model.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceResponseDTO {
    private Long id;
    private Boolean smoking;
    private Boolean pets;
    private Boolean visitors;
    private Boolean partying;
    private Boolean sharingBedroom;
    private Boolean hasApartment;
}
