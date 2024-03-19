package com.example.roomiematch.repository;

import com.example.roomiematch.model.entities.QuestionnaireResponse;
import com.example.roomiematch.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionnaireResponseRepository extends JpaRepository<QuestionnaireResponse, Long> {
    Optional<QuestionnaireResponse> findByUserId(Long userId);
    List<QuestionnaireResponse> findAllByUserId(Long userId);
    Optional<QuestionnaireResponse> findByUserIdAndQuestionId(Long userId, Long questionId);
    @Query("SELECT qr FROM QuestionnaireResponse qr JOIN qr.user u WHERE u.email != :userEmail")
    List<QuestionnaireResponse> findAllByUserEmailNot(@Param("userEmail") String userEmail);
    List<QuestionnaireResponse> findByUserEmail(String userEmail);
    @Query("SELECT qr FROM QuestionnaireResponse qr WHERE qr.user = :user")
    List<QuestionnaireResponse> findAllByUser(User user);
}
