package com.example.roomiematch.repository;

import com.example.roomiematch.model.entities.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    Optional<Choice> findByChoiceText(String choiceText);
    boolean existsByChoiceText(String choiceText);
}

