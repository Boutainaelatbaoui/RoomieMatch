package com.example.roomiematch.service;

import com.example.roomiematch.model.dto.request.QuestionRequestDTO;
import com.example.roomiematch.model.dto.response.QuestionResponseDTO;
import com.example.roomiematch.model.entities.Question;

import java.util.List;

public interface IQuestionService {
    List<QuestionResponseDTO> getAllQuestions();
    QuestionResponseDTO getQuestionById(Long id);
    QuestionRequestDTO createQuestion(QuestionRequestDTO questionRequestDTO);
    QuestionResponseDTO updateQuestion(Long id, QuestionRequestDTO updatedQuestionRequestDTO);
    void deleteQuestion(Long id);
}

