package com.example.taskscheduler.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BucketDTO {
    private Long id;

    private String name;

    private List<TaskDTO> tasks;
}
