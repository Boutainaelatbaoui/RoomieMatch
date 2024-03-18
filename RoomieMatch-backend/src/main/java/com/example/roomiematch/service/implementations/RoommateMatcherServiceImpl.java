package com.example.roomiematch.service.implementations;

import com.example.roomiematch.mapper.UserMapper;
import com.example.roomiematch.model.dto.response.RoommateMatchDTO;
import com.example.roomiematch.model.entities.QuestionnaireResponse;
import com.example.roomiematch.model.entities.User;
import com.example.roomiematch.repository.QuestionnaireResponseRepository;
import com.example.roomiematch.repository.UserRepository;
import com.example.roomiematch.service.IRoommateMatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoommateMatcherServiceImpl implements IRoommateMatcherService {

    private final QuestionnaireResponseRepository responseRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public RoommateMatcherServiceImpl(QuestionnaireResponseRepository responseRepository, UserRepository userRepository, UserMapper userMapper) {
        this.responseRepository = responseRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<RoommateMatchDTO> findRoommatesForUserByEmail(String userEmail) {
        List<QuestionnaireResponse> userResponses = responseRepository.findByUserEmail(userEmail);
        Map<Long, List<QuestionnaireResponse>> allResponsesByUser = responseRepository.findAllByUserEmailNot(userEmail)
                .stream()
                .collect(Collectors.groupingBy(response -> response.getUser().getId()));

        List<RoommateMatchDTO> roommateMatches = new ArrayList<>();
        for (Map.Entry<Long, List<QuestionnaireResponse>> entry : allResponsesByUser.entrySet()) {
            Long roommateUserId = entry.getKey();
            List<QuestionnaireResponse> roommateResponses = entry.getValue();
            int matchScore = calculateMatchScore(userResponses, roommateResponses);
            roommateMatches.add(RoommateMatchDTO.builder()
                    .user(userMapper.toDTO(userRepository.findById(roommateUserId).orElse(new User())))
                    .matchScore(matchScore)
                    .percentageMatch(0.0)
                    .build());
        }
        roommateMatches.sort(Comparator.comparingInt(RoommateMatchDTO::getMatchScore).reversed());

        int totalQuestions = userResponses.size();
        for (RoommateMatchDTO match : roommateMatches) {
            match.setPercentageMatch((double) match.getMatchScore() / totalQuestions * 100);
        }

        return roommateMatches;
    }


    private int calculateMatchScore(List<QuestionnaireResponse> userResponses, List<QuestionnaireResponse> roommateResponses) {
        int matchScore = 0;
        for (QuestionnaireResponse userResponse : userResponses) {
            for (QuestionnaireResponse roommateResponse : roommateResponses) {
                if (userResponse.getQuestion().equals(roommateResponse.getQuestion()) &&
                        userResponse.getChoice().equals(roommateResponse.getChoice())) {
                    matchScore++;
                    break;
                }
            }
        }
        return matchScore;
    }
}

