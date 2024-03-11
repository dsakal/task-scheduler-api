package com.example.taskscheduler.services.impl;

import com.example.taskscheduler.model.entities.Task;
import com.example.taskscheduler.model.entities.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class NotificationService {
    private final TaskServiceImpl taskService;
    private final EmailService emailService;
    private final MappingServiceImpl mappingService;
    public NotificationService(TaskServiceImpl taskService, EmailService emailService, MappingServiceImpl mappingService) {
        this.taskService = taskService;
        this.emailService = emailService;
        this.mappingService = mappingService;
    }
    @Scheduled(cron = "0 0 23 * * *")
    public void sendDeadlineNotification() {
        List<Task> tasksDueToday = taskService.findAllByDueDate(LocalDate.now()).stream().map(mappingService::mapDtoToTask).toList();
        for (Task task : tasksDueToday) {
            for (User user : task.getAssignedUsers()) {
                String emailContent = "Reminder: Your task \"" + task.getName() + "\" is due in one hour.";
                emailService.sendEmail(user.getEmail(), "Task Deadline Reminder", emailContent);
            }
        }
    }
}
