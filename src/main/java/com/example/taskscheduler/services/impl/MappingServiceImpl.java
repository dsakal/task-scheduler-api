package com.example.taskscheduler.services.impl;

import com.example.taskscheduler.model.dto.*;
import com.example.taskscheduler.model.entities.*;
import com.example.taskscheduler.services.MappingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class MappingServiceImpl implements MappingService {
    @Override
    public Bucket mapDtoToBucket(BucketDTO bucketDto){
        return Bucket.builder()
                .id(bucketDto.getId())
                .name(bucketDto.getName())
                .tasks(bucketDto.getTasks().stream()
                        .map(this::mapDtoToTask)
                        .collect(Collectors.toList()))
                .build();
    }
    @Override
    public BucketDTO mapBucketToDto(Bucket bucket){
        return BucketDTO.builder()
                .id(bucket.getId())
                .name(bucket.getName())
                .tasks(bucket.getTasks().stream()
                        .map(this::mapTaskToDto)
                        .collect(Collectors.toList()))
                .build();
    }
    @Override
    public Bucket mapDtoToBucketWithoutTasks(BucketDTO bucketDto){
        return Bucket.builder()
                .id(bucketDto.getId())
                .name(bucketDto.getName())
                .build();
    }
    @Override
    public BucketDTO mapBucketToDtoWithoutTasks(Bucket bucket){
        return BucketDTO.builder()
                .id(bucket.getId())
                .name(bucket.getName())
                .build();
    }
    @Override
    public Task mapDtoToTask(TaskDTO taskDTO){
        Task task = Task.builder()
                .id(taskDTO.getId())
                .name(taskDTO.getName())
                .description(taskDTO.getDescription())
                .priority(taskDTO.getPriority())
                .image(taskDTO.getImage())
                .dueDate(LocalDate.parse(taskDTO.getDueDate().format(DateTimeFormatter.ISO_DATE)))
                .createdAt(taskDTO.getCreatedAt())
                .build();

        if (taskDTO.getCreatedBy() != null){
            task.setCreatedBy(mapDtoToUser(taskDTO.getCreatedBy()));
        }
        if (taskDTO.getBucket() != null){
            task.setBucket(mapDtoToBucketWithoutTasks(taskDTO.getBucket()));
        }
        if (taskDTO.getAssignedUsers() != null){
            task.setAssignedUsers(taskDTO.getAssignedUsers().stream()
                    .map(this::mapDtoToUser)
                    .collect(Collectors.toList()));
        }
        return task;
    }
    @Override
    public TaskDTO mapTaskToDto(Task task){
        TaskDTO taskDTO = TaskDTO.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .priority(task.getPriority())
                .image(task.getImage())
                .dueDate(LocalDate.parse(task.getDueDate().format(DateTimeFormatter.ISO_DATE)))
                .createdAt(task.getCreatedAt())
                .build();

        if (task.getCreatedBy() != null){
            taskDTO.setCreatedBy(mapUserToDto(task.getCreatedBy()));
        }
        if (task.getBucket() != null){
            taskDTO.setBucket(mapBucketToDtoWithoutTasks(task.getBucket()));
        }
        if (task.getAssignedUsers() != null){
            taskDTO.setAssignedUsers(task.getAssignedUsers().stream()
                    .map(this::mapUserToDto)
                    .collect(Collectors.toList()));
        }
        return taskDTO;
    }
    @Override
    public User mapDtoToUser(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .build();
    }

    @Override
    public UserDTO mapUserToDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
