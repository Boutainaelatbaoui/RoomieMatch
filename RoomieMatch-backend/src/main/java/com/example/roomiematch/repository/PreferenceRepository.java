package com.example.roomiematch.repository;

import com.example.roomiematch.model.entities.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
}
