package com.example.taskscheduler.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
    private Long id;

    private String name;

    private String description;

    private String priority;

    private String image;

    private LocalDate dueDate;

    private LocalTime createdAt;

    private UserDTO createdBy;

    private List<UserDTO> assignedUsers;

    @JsonIgnore
    private BucketDTO bucket;
}
