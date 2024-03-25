package com.example.roomiematch.service.implementations;

import com.example.roomiematch.mapper.RequestMapper;
import com.example.roomiematch.model.dto.request.RequestRequestDTO;
import com.example.roomiematch.model.dto.response.RequestResponseDTO;
import com.example.roomiematch.model.entities.User;
import com.example.roomiematch.repository.RequestRepository;
import com.example.roomiematch.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RequestServiceImplTest {

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RequestMapper requestMapper;

    @InjectMocks
    private RequestServiceImpl requestService;




}
