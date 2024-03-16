package com.example.roomiematch.mapper;

import com.example.roomiematch.model.dto.request.QuestionnaireResponseRequestDTO;
import com.example.roomiematch.model.dto.response.QuestionnaireResponseResponseDTO;
import com.example.roomiematch.model.entities.QuestionnaireResponse;
import com.example.roomiematch.repository.ChoiceRepository;
import com.example.roomiematch.repository.QuestionRepository;
import com.example.roomiematch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionnaireResponseMapper {

    private final UserRepository userRepository;
    private final QuestionMapper questionMapper;
    private final ChoiceRepository choiceRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceMapper choiceMapper;

    @Autowired
    public QuestionnaireResponseMapper(ChoiceMapper choiceMapper,QuestionRepository questionRepository ,UserRepository userRepository, QuestionMapper questionMapper, ChoiceRepository choiceRepository) {
        this.userRepository = userRepository;
        this.questionMapper = questionMapper;
        this.choiceRepository = choiceRepository;
        this.questionRepository = questionRepository;
        this.choiceMapper = choiceMapper;
    }

    public QuestionnaireResponse toQuestionnaireResponse(QuestionnaireResponseRequestDTO requestDTO) {
        QuestionnaireResponse response = new QuestionnaireResponse();
        response.setUser(userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
        response.setQuestion(questionRepository.findById(requestDTO.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Question not found")));
        response.setChoice(choiceRepository.findById(requestDTO.getChoiceId())
                .orElseThrow(() -> new IllegalArgumentException("Choice not found")));
        return response;
    }


    public QuestionnaireResponseResponseDTO toQuestionnaireResponseDto(QuestionnaireResponse response) {
        QuestionnaireResponseResponseDTO responseDTO = new QuestionnaireResponseResponseDTO();
        responseDTO.setId(response.getId());
        responseDTO.setUserId(response.getUser().getId());
        responseDTO.setQuestion(questionMapper.toDTO(response.getQuestion()));
        responseDTO.setChoice(choiceMapper.toDTO(response.getChoice()));
        return responseDTO;
    }
}
