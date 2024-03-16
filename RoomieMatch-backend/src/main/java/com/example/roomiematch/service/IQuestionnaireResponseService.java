package com.example.roomiematch.service;

import com.example.roomiematch.model.dto.request.QuestionnaireResponseRequestDTO;
import com.example.roomiematch.model.dto.response.QuestionnaireResponseResponseDTO;

import java.util.List;

public interface IQuestionnaireResponseService {
    QuestionnaireResponseResponseDTO saveResponse(QuestionnaireResponseRequestDTO request);
    List<QuestionnaireResponseResponseDTO> getAllResponses();
    QuestionnaireResponseResponseDTO getResponseById(Long id);
    List<QuestionnaireResponseResponseDTO> getAllResponsesByUserId(Long userId);
    void deleteResponseById(Long id);
}

