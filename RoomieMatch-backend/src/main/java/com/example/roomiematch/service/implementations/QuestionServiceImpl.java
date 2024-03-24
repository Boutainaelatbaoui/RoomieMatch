package com.example.roomiematch.service.implementations;

import com.example.roomiematch.mapper.QuestionMapper;
import com.example.roomiematch.model.dto.response.QuestionResponseDTO;
import com.example.roomiematch.model.dto.request.QuestionRequestDTO;
import com.example.roomiematch.model.entities.Question;
import com.example.roomiematch.repository.QuestionRepository;
import com.example.roomiematch.service.IQuestionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import jakarta.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    @Override
    public List<QuestionResponseDTO> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questionMapper.toDTOList(questions);
    }

    @Override
    public List<QuestionResponseDTO> getTenQuestions() {
        List<Question> questions = questionRepository.findFirst10ByOrderByIdAsc();
        return questionMapper.toDTOList(questions);
    }

    @Override
    public QuestionResponseDTO getQuestionById(Long id) {
        Question question = questionRepository.findById(id).orElse(null);
        if (question == null) {
            throw new EntityNotFoundException("Question not found with id: " + id);
        } else {
            return questionMapper.toDTO(question);
        }
    }

    @Override
    public QuestionRequestDTO createQuestion(QuestionRequestDTO questionRequestDTO) {
        if (questionRepository.existsByQuestionText(questionRequestDTO.getQuestionText())) {
            throw new ValidationException("Question with the same text already exists");
        }
        Question question = questionMapper.toEntity(questionRequestDTO);
        return questionMapper.toRequestDTO(questionRepository.save(question));
    }

    @Override
    public QuestionResponseDTO updateQuestion(Long id, QuestionRequestDTO updatedQuestionRequestDTO) {
        Optional<Question> existingQuestion = questionRepository.findById(id);
        if (existingQuestion.isPresent()) {
            Question questionToUpdate = existingQuestion.get();
            questionToUpdate.setQuestionText(updatedQuestionRequestDTO.getQuestionText());
            return questionMapper.toDTO(questionRepository.save(questionToUpdate));
        } else {
            throw new EntityNotFoundException("Question not found with id: " + id);
        }
    }

    @Override
    public void deleteQuestion(Long id) {
        if(questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Question not found with id: " + id);
        }
    }
}
