package com.example.roomiematch.service.implementations;

import com.example.roomiematch.mapper.QuestionnaireResponseMapper;
import com.example.roomiematch.model.dto.request.QuestionnaireResponseRequestDTO;
import com.example.roomiematch.model.dto.response.QuestionnaireResponseResponseDTO;
import com.example.roomiematch.model.entities.Question;
import com.example.roomiematch.model.entities.QuestionnaireResponse;
import com.example.roomiematch.model.entities.User;
import com.example.roomiematch.repository.ChoiceRepository;
import com.example.roomiematch.repository.QuestionRepository;
import com.example.roomiematch.repository.QuestionnaireResponseRepository;
import com.example.roomiematch.repository.UserRepository;
import com.example.roomiematch.service.IQuestionnaireResponseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionnaireResponseServiceImpl implements IQuestionnaireResponseService {

    private final QuestionnaireResponseRepository responseRepository;
    private final QuestionnaireResponseMapper responseMapper;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    @Autowired
    public QuestionnaireResponseServiceImpl(QuestionnaireResponseRepository responseRepository, QuestionnaireResponseMapper responseMapper, UserRepository userRepository, QuestionRepository questionRepository, ChoiceRepository choiceRepository) {
        this.responseRepository = responseRepository;
        this.responseMapper = responseMapper;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
    }

    @Override

    public List<QuestionnaireResponseResponseDTO> saveResponses(List<QuestionnaireResponseRequestDTO> requests) {
        List<QuestionnaireResponseResponseDTO> responses = new ArrayList<>();

        for (QuestionnaireResponseRequestDTO request : requests) {
            validateUserQuestionAndChoiceExistence(request.getUserId(), request.getQuestionId(), request.getChoiceId());

            Question question = questionRepository.findById(request.getQuestionId())
                    .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + request.getQuestionId()));

            boolean choiceExistsForQuestion = question.getChoices().stream()
                    .anyMatch(choice -> choice.getId().equals(request.getChoiceId()));
            if (!choiceExistsForQuestion) {
                throw new EntityNotFoundException("Choice with id " + request.getChoiceId() + " does not belong to the provided question");
            }

            Optional<QuestionnaireResponse> existingResponse = responseRepository.findByUserIdAndQuestionId(
                    request.getUserId(), request.getQuestionId());

            if (existingResponse.isPresent()) {
                QuestionnaireResponse responseToUpdate = existingResponse.get();
                responseToUpdate.setChoice(choiceRepository.findById(request.getChoiceId())
                        .orElseThrow(() -> new EntityNotFoundException("Choice not found with id: " + request.getChoiceId())));

                QuestionnaireResponse updatedResponse = responseRepository.save(responseToUpdate);
                responses.add(responseMapper.toQuestionnaireResponseDto(updatedResponse));
            } else {
                QuestionnaireResponse response = responseMapper.toQuestionnaireResponse(request);
                QuestionnaireResponse savedResponse = responseRepository.save(response);
                responses.add(responseMapper.toQuestionnaireResponseDto(savedResponse));
            }
        }

        return responses;
    }



    @Override
    public List<QuestionnaireResponseResponseDTO> getAllResponses() {
        List<QuestionnaireResponse> responses = responseRepository.findAll();
        return responses.stream()
                .map(responseMapper::toQuestionnaireResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionnaireResponseResponseDTO getResponseById(Long id) {
        Optional<QuestionnaireResponse> responseOptional = responseRepository.findById(id);
        if (responseOptional.isPresent()) {
            QuestionnaireResponse response = responseOptional.get();
            return responseMapper.toQuestionnaireResponseDto(response);
        }
        throw new EntityNotFoundException("Response not found with id: " + id);
    }

    @Override
    public void deleteResponseById(Long id) {
        if (!responseRepository.existsById(id)) {
            throw new EntityNotFoundException("Response not found with id: " + id);
        }
        responseRepository.deleteById(id);
    }

    @Override
    public List<QuestionnaireResponseResponseDTO> getAllResponsesByUserEmail(String userEmail) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found with email: " + userEmail);
        }
        User user = userOptional.get();
        List<QuestionnaireResponse> responses = responseRepository.findAllByUser(user);
        return responses.stream()
                .map(responseMapper::toQuestionnaireResponseDto)
                .collect(Collectors.toList());
    }



    private void validateUserQuestionAndChoiceExistence(Long userId, Long questionId, Long choiceId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));

        choiceRepository.findById(choiceId)
                .orElseThrow(() -> new EntityNotFoundException("Choice not found with id: " + choiceId));
    }
}
