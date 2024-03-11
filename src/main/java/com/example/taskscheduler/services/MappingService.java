package com.example.taskscheduler.services;

import com.example.taskscheduler.model.dto.BucketDTO;
import com.example.taskscheduler.model.dto.TaskDTO;
import com.example.taskscheduler.model.dto.UserDTO;
import com.example.taskscheduler.model.entities.Bucket;
import com.example.taskscheduler.model.entities.Task;
import com.example.taskscheduler.model.entities.User;

public interface MappingService {
    Bucket mapDtoToBucket(BucketDTO bucketDto);
    BucketDTO mapBucketToDto(Bucket bucket);
    Bucket mapDtoToBucketWithoutTasks(BucketDTO bucketDto);
    BucketDTO mapBucketToDtoWithoutTasks(Bucket bucket);
    Task mapDtoToTask(TaskDTO taskDTO);
    TaskDTO mapTaskToDto(Task task);
    User mapDtoToUser(UserDTO userDTO);
    UserDTO mapUserToDto(User user);
}
