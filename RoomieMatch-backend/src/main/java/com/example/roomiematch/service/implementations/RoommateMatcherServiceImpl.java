package com.example.roomiematch.service.implementations;

import com.example.roomiematch.mapper.UserMapper;
import com.example.roomiematch.model.dto.response.UserResponseDTO;
import com.example.roomiematch.model.entities.QuestionnaireResponse;
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
    public List<UserResponseDTO> findRoommatesForUserByEmail(String userEmail) {
        List<QuestionnaireResponse> userResponses = responseRepository.findByUserEmail(userEmail);
        Map<Long, List<QuestionnaireResponse>> allResponsesByUser = responseRepository.findAllByUserEmailNot(userEmail)
                .stream()
                .collect(Collectors.groupingBy(response -> response.getUser().getId()));

        List<UserResponseDTO> roommateMatches = new ArrayList<>();
        for (Map.Entry<Long, List<QuestionnaireResponse>> entry : allResponsesByUser.entrySet()) {
            Long roommateUserId = entry.getKey();
            List<QuestionnaireResponse> roommateResponses = entry.getValue();
            int matchScore = calculateMatchScore(userResponses, roommateResponses);

            UserResponseDTO roommateDetails = userRepository.findById(roommateUserId)
                    .map(userMapper::toDTO)
                    .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + roommateUserId));

            roommateDetails.setMatchScore(matchScore);
            double percentageMatch = (double) matchScore / userResponses.size() * 100;
            roommateDetails.setPercentageMatch(percentageMatch);

            roommateMatches.add(roommateDetails);
        }

        roommateMatches.sort(Comparator.comparingInt(UserResponseDTO::getMatchScore).reversed());

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

