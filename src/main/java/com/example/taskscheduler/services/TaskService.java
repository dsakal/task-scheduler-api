package com.example.taskscheduler.services;

import com.example.taskscheduler.model.dto.TaskDTO;
import com.example.taskscheduler.model.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskDTO> findAll();
    Optional<TaskDTO> findTaskById(Long taskId);
    TaskDTO createTask(TaskDTO taskDTO);
    Optional<TaskDTO> addUsersToTask(Long taskId, List<UserDTO> userDTOs);
    void deleteById(Long id);
    void uploadImage(MultipartFile image, Long taskId) throws IOException;
    List<TaskDTO> findAllByBucketId(Long id);
    List<TaskDTO> findAllByDueDate(LocalDate dueDate);
    List<TaskDTO> filterTasks(Integer numberOfTasks, LocalDate startDate, LocalDate endDate, List<UserDTO> users);
}
