package com.example.roomiematch.repository;

import com.example.roomiematch.model.entities.Preference;
import com.example.roomiematch.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    Optional<Preference> findByUserId(Long userId);
    Optional<Preference> findByUser(User user);
}
