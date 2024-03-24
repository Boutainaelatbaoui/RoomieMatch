package com.example.roomiematch.repository;

import com.example.roomiematch.model.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    boolean existsByQuestionText(String questionText);
    List<Question> findFirst10ByOrderByIdAsc();
}

