package com.example.roomiematch.service.implementations;

import com.example.roomiematch.mapper.ChoiceMapper;
import com.example.roomiematch.model.dto.request.ChoiceRequestDTO;
import com.example.roomiematch.model.dto.response.ChoiceResponseDTO;
import com.example.roomiematch.model.entities.Choice;
import com.example.roomiematch.model.entities.Question;
import com.example.roomiematch.repository.ChoiceRepository;
import com.example.roomiematch.repository.QuestionRepository;
import com.example.roomiematch.service.IChoiceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import jakarta.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class ChoiceServiceImpl implements IChoiceService {

    private final ChoiceRepository choiceRepository;
    private final ChoiceMapper choiceMapper;
    private final QuestionRepository questionRepository;

    @Autowired
    public ChoiceServiceImpl(ChoiceRepository choiceRepository, ChoiceMapper choiceMapper, QuestionRepository questionRepository) {
        this.choiceRepository = choiceRepository;
        this.choiceMapper = choiceMapper;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<ChoiceResponseDTO> getAllChoices() {
        List<Choice> choices = choiceRepository.findAll();
        return choiceMapper.toDTOList(choices);
    }

    @Override
    public ChoiceResponseDTO getChoiceById(Long id) {
        Choice choice = choiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Choice not found with id: " + id));
        return choiceMapper.toDTO(choice);
    }

    @Override
    public ChoiceResponseDTO updateChoice(Long id, ChoiceRequestDTO updatedChoiceRequestDTO) {
        Optional<Choice> existingChoice = choiceRepository.findById(id);
        if (existingChoice.isPresent()) {
            Choice choiceToUpdate = existingChoice.get();
            validateQuestionId(updatedChoiceRequestDTO.getQuestionId());
            choiceToUpdate.setChoiceText(updatedChoiceRequestDTO.getChoiceText());
            choiceToUpdate.setQuestion(validateQuestionId(updatedChoiceRequestDTO.getQuestionId()));
            return choiceMapper.toDTO(choiceRepository.save(choiceToUpdate));
        } else {
            throw new EntityNotFoundException("Choice not found with id: " + id);
        }
    }

    @Override
    public ChoiceResponseDTO createChoice(ChoiceRequestDTO choiceRequestDTO) {
//        validateChoiceText(choiceRequestDTO.getChoiceText());
        validateQuestionId(choiceRequestDTO.getQuestionId());
        if(choiceRepository.existsByChoiceTextAndQuestionId(choiceRequestDTO.getChoiceText(), choiceRequestDTO.getQuestionId())){
            throw new ValidationException("Choice with the same text already exists");
        }
        Choice choice = choiceMapper.toEntity(choiceRequestDTO);
        choice.setQuestion(validateQuestionId(choiceRequestDTO.getQuestionId()));

        return choiceMapper.toDTO(choiceRepository.save(choice));
    }

    private Question validateQuestionId(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new EntityNotFoundException("Question not found with id: " + questionId);
        } else {
            return questionRepository.findById(questionId).get();
        }
    }

    private void validateChoiceText(String choiceText) {
        if (choiceRepository.existsByChoiceText(choiceText)) {
            throw new ValidationException("Choice with the same text already exists");
        }
    }


    @Override
    public void deleteChoice(Long id) {
        if (choiceRepository.existsById(id)) {
            choiceRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Choice not found with id: " + id);
        }
    }
}

