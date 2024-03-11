package com.example.taskscheduler.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String priority;

    private String image;

    private LocalDate dueDate;

    private LocalTime createdAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @ManyToMany
    @JoinTable(
            name = "task_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> assignedUsers;

    @ManyToOne
    @JoinColumn(name = "bucket_id")
    private Bucket bucket;
}
