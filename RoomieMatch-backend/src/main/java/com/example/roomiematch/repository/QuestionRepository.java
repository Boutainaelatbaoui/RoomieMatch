package com.example.roomiematch.repository;

import com.example.roomiematch.model.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    boolean existsByQuestionText(String questionText);
}

