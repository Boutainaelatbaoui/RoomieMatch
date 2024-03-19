package com.example.roomiematch.service;

import com.example.roomiematch.model.dto.request.QuestionnaireResponseRequestDTO;
import com.example.roomiematch.model.dto.response.QuestionnaireResponseResponseDTO;

import java.util.List;

public interface IQuestionnaireResponseService {
    List<QuestionnaireResponseResponseDTO> saveResponses(List<QuestionnaireResponseRequestDTO> requests);
    List<QuestionnaireResponseResponseDTO> getAllResponses();
    QuestionnaireResponseResponseDTO getResponseById(Long id);
    List<QuestionnaireResponseResponseDTO> getAllResponsesByUserEmail(String userEmail);
    void deleteResponseById(Long id);
}

