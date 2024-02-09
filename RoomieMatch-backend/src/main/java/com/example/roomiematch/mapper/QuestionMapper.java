package com.example.roomiematch.mapper;

import com.example.roomiematch.model.dto.response.ChoiceResponseDTO;
import com.example.roomiematch.model.dto.response.QuestionResponseDTO;
import com.example.roomiematch.model.dto.request.QuestionRequestDTO;
import com.example.roomiematch.model.entities.Choice;
import com.example.roomiematch.model.entities.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {

    private final ChoiceMapper choiceMapper;

    @Autowired
    public QuestionMapper(ChoiceMapper choiceMapper) {
        this.choiceMapper = choiceMapper;
    }

    public Question toEntity(QuestionRequestDTO questionRequestDTO) {
        return Question.builder()
                .questionText(questionRequestDTO.getQuestionText())
                .build();
    }

    public QuestionResponseDTO toDTO(Question question) {
        return QuestionResponseDTO.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .choices(choiceMapper.toDTOList(question.getChoices()))
                .build();
    }

    public QuestionRequestDTO toRequestDTO(Question question) {
        return QuestionRequestDTO.builder()
                .questionText(question.getQuestionText())
                .build();
    }
    public List<QuestionResponseDTO> toDTOList(List<Question> questions) {
        return questions.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

