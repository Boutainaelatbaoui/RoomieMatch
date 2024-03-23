package com.example.roomiematch.service.implementations;

import com.example.roomiematch.mapper.UserMapper;
import com.example.roomiematch.model.dto.request.RegisterRequest;
import com.example.roomiematch.model.dto.response.UserResponseDTO;
import com.example.roomiematch.model.entities.City;
import com.example.roomiematch.model.entities.User;
import com.example.roomiematch.repository.CityRepository;
import com.example.roomiematch.repository.UserRepository;
import com.example.roomiematch.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CityRepository cityRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponseDTO> getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        } else {
            return Optional.of(userMapper.toDTO(user.get()));
        }
    }

    @Override
    public List<UserResponseDTO> getUsersByName(String name) {
        List<User> users = userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponseDTO> getUserDetailsByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found with email: " + email);
        }
        return user.map(userMapper::toDTO);
    }

    @Override
    public void updateUserDetailsByEmail(String email, RegisterRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setTelephone(request.getTelephone());
        user.setEmail(request.getEmail());
        user.setBio(request.getBio());
        user.setBudget(request.getBudget());
        user.setOccupation(request.getOccupation());
        user.setBirthdate(request.getBirthdate());
        user.setCurrentCity(cityRepository.findById(request.getCurrentCityId()).get());
        user.setDesiredCity(cityRepository.findById(request.getDesiredCityId()).get());

        userRepository.save(user);
    }
}

