package com.example.roomiematch.service.implementations;

import com.example.roomiematch.mapper.QuestionMapper;
import com.example.roomiematch.model.dto.request.QuestionRequestDTO;
import com.example.roomiematch.model.dto.response.QuestionResponseDTO;
import com.example.roomiematch.model.entities.Question;
import com.example.roomiematch.repository.QuestionRepository;
import com.example.roomiematch.service.implementations.QuestionServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionMapper questionMapper;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Test
    void getAllQuestions() {
        when(questionRepository.findAll()).thenReturn(Collections.singletonList(new Question()));

        when(questionMapper.toDTOList(any())).thenReturn(Collections.singletonList(new QuestionResponseDTO()));

        assertNotNull(questionService.getAllQuestions());
    }

    @Test
    void getQuestionById_ExistingId() {
        Long existingId = 1L;
        Question existingQuestion = new Question();
        existingQuestion.setId(existingId);

        when(questionRepository.findById(existingId)).thenReturn(Optional.of(existingQuestion));

        when(questionMapper.toDTO(existingQuestion)).thenReturn(new QuestionResponseDTO());

        assertNotNull(questionService.getQuestionById(existingId));
    }

    @Test
    void getQuestionById_NonExistingId() {
        Long nonExistingId = 2L;

        when(questionRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> questionService.getQuestionById(nonExistingId));
    }

    @Test
    void createQuestion_Success() {
        when(questionRepository.existsByQuestionText(any())).thenReturn(false);

        when(questionMapper.toEntity(any())).thenReturn(new Question());
        when(questionMapper.toRequestDTO(any())).thenReturn(new QuestionRequestDTO());

        assertNotNull(questionService.createQuestion(new QuestionRequestDTO()));
    }

    @Test
    void createQuestion_DuplicateText() {
        when(questionRepository.existsByQuestionText(any())).thenReturn(true);

        assertThrows(ValidationException.class, () -> questionService.createQuestion(new QuestionRequestDTO()));
    }

    @Test
    void updateQuestion_ExistingId() {
        Long existingId = 1L;
        Question existingQuestion = new Question();
        existingQuestion.setId(existingId);

        when(questionRepository.findById(existingId)).thenReturn(Optional.of(existingQuestion));

        when(questionMapper.toDTO(existingQuestion)).thenReturn(new QuestionResponseDTO());

        when(questionRepository.save(any())).thenReturn(existingQuestion);

        assertNotNull(questionService.updateQuestion(existingId, new QuestionRequestDTO()));
    }

    @Test
    void updateQuestion_NonExistingId() {
        Long nonExistingId = 2L;

        when(questionRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                questionService.updateQuestion(nonExistingId, new QuestionRequestDTO()));
    }

    @Test
    void deleteQuestion_ExistingId() {
        Long existingId = 1L;

        when(questionRepository.existsById(existingId)).thenReturn(true);

        assertDoesNotThrow(() -> questionService.deleteQuestion(existingId));
    }

    @Test
    void deleteQuestion_NonExistingId() {
        Long nonExistingId = 2L;

        when(questionRepository.existsById(nonExistingId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> questionService.deleteQuestion(nonExistingId));
    }

}
