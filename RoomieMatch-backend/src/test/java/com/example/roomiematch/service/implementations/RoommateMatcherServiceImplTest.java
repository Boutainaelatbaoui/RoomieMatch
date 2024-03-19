package com.example.roomiematch.service.implementations;

import com.example.roomiematch.mapper.UserMapper;
import com.example.roomiematch.model.dto.response.RoommateMatchDTO;
import com.example.roomiematch.model.entities.QuestionnaireResponse;
import com.example.roomiematch.model.entities.User;
import com.example.roomiematch.repository.QuestionnaireResponseRepository;
import com.example.roomiematch.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoommateMatcherServiceImplTest {

    @Mock
    private QuestionnaireResponseRepository responseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private RoommateMatcherServiceImpl roommateMatcherService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindRoommatesForUserByEmail_NoMatches() {
        List<QuestionnaireResponse> userResponses = new ArrayList<>();

        when(responseRepository.findByUserEmail(anyString())).thenReturn(userResponses);
        when(responseRepository.findAllByUserEmailNot(anyString())).thenReturn(new ArrayList<>());

        List<RoommateMatchDTO> roommateMatches = roommateMatcherService.findRoommatesForUserByEmail("test@example.com");

        assert(roommateMatches.isEmpty());
    }

    @Test
    public void testFindRoommatesForUserByEmail_WithMatches() {
        // Scenario: User has matches

        // Mock user responses
        List<QuestionnaireResponse> userResponses = new ArrayList<>();
        // Add user responses to the list

        // Mock roommate responses
        List<QuestionnaireResponse> roommateResponses = new ArrayList<>();
        // Add roommate responses to the list

        // Mock UserRepository to return a user
        User user = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // Mock responseRepository to return roommate responses
        when(responseRepository.findByUserEmail(anyString())).thenReturn(userResponses);
        when(responseRepository.findAllByUserEmailNot(anyString())).thenReturn(roommateResponses);

        // Mock userMapper
        // when(userMapper.toDTO(any(User.class))).thenReturn(new UserDTO());

        // Call the method under test
        List<RoommateMatchDTO> roommateMatches = roommateMatcherService.findRoommatesForUserByEmail("test@example.com");

        // Assert the result
        assert(roommateMatches.isEmpty()); // Add your assertions here
    }

}
