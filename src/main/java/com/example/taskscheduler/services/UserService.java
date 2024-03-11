package com.example.taskscheduler.services;

import com.example.taskscheduler.model.dto.UserDTO;
import com.example.taskscheduler.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDTO> findUserById(Long id);
    List<UserDTO> findAll();
}
