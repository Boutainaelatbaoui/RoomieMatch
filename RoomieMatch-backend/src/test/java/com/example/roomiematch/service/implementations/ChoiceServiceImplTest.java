package com.example.roomiematch.service.implementations;

import com.example.roomiematch.mapper.ChoiceMapper;
import com.example.roomiematch.model.dto.request.ChoiceRequestDTO;
import com.example.roomiematch.model.dto.response.ChoiceResponseDTO;
import com.example.roomiematch.model.entities.Choice;
import com.example.roomiematch.model.entities.Question;
import com.example.roomiematch.repository.ChoiceRepository;
import com.example.roomiematch.repository.QuestionRepository;
import com.example.roomiematch.service.IChoiceService;
import com.example.roomiematch.service.implementations.ChoiceServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ChoiceServiceImplTest {

    @Mock
    private ChoiceRepository choiceRepository;

    @Mock
    private ChoiceMapper choiceMapper;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private ChoiceServiceImpl choiceService;

    @Test
    void getAllChoices() {
        when(choiceRepository.findAll()).thenReturn(Collections.singletonList(new Choice()));

        when(choiceMapper.toDTOList(any())).thenReturn(Collections.singletonList(new ChoiceResponseDTO()));

        assertNotNull(choiceService.getAllChoices());
    }

    @Test
    void getChoiceById_ExistingId() {
        Long existingId = 1L;
        Choice existingChoice = new Choice();
        existingChoice.setId(existingId);

        when(choiceRepository.findById(existingId)).thenReturn(Optional.of(existingChoice));

        when(choiceMapper.toDTO(existingChoice)).thenReturn(new ChoiceResponseDTO());

        assertNotNull(choiceService.getChoiceById(existingId));
    }

    @Test
    void getChoiceById_NonExistingId() {
        Long nonExistingId = 2L;

        when(choiceRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> choiceService.getChoiceById(nonExistingId));
    }

    @Test
    void updateChoice_NonExistingId() {
        Long nonExistingId = 2L;

        when(choiceRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                choiceService.updateChoice(nonExistingId, new ChoiceRequestDTO()));
    }

    @Test
    void createChoice_DuplicateText() {
        when(choiceRepository.existsByChoiceText(any())).thenReturn(true);

        when(questionRepository.existsById(any())).thenReturn(true);

        assertThrows(ValidationException.class, () -> choiceService.createChoice(new ChoiceRequestDTO()));
    }

    @Test
    void createChoice_InvalidQuestionId() {
        when(choiceRepository.existsByChoiceText(any())).thenReturn(false);

        when(questionRepository.existsById(any())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> choiceService.createChoice(new ChoiceRequestDTO()));
    }

    @Test
    void deleteChoice_ExistingId() {
        Long existingId = 1L;

        when(choiceRepository.existsById(existingId)).thenReturn(true);

        assertDoesNotThrow(() -> choiceService.deleteChoice(existingId));
    }

    @Test
    void deleteChoice_NonExistingId() {
        Long nonExistingId = 2L;

        when(choiceRepository.existsById(nonExistingId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> choiceService.deleteChoice(nonExistingId));
    }
}
