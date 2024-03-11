package com.example.taskscheduler.services.impl;

import com.example.taskscheduler.model.dto.UserDTO;
import com.example.taskscheduler.model.entities.User;
import com.example.taskscheduler.repositories.UserRepository;
import com.example.taskscheduler.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MappingServiceImpl mappingService;

    public UserServiceImpl(UserRepository userRepository, MappingServiceImpl mappingService) {
        this.userRepository = userRepository;
        this.mappingService = mappingService;
    }

    @Override
    public Optional<UserDTO> findUserById(Long id) {
        return userRepository.findById(id).map(mappingService::mapUserToDto);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(mappingService::mapUserToDto)
                .collect(Collectors.toList());
    }
}
