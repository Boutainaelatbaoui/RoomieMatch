package com.example.roomiematch.repository;

import com.example.roomiematch.model.entities.QuestionnaireResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionnaireResponseRepository extends JpaRepository<QuestionnaireResponse, Long> {
    Optional<QuestionnaireResponse> findByUserId(Long userId);
    List<QuestionnaireResponse> findAllByUserId(Long userId);
    Optional<QuestionnaireResponse> findByUserIdAndQuestionId(Long userId, Long questionId);

}
