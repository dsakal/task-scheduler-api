package com.example.taskscheduler.services.impl;

import com.example.taskscheduler.model.dto.TaskDTO;
import com.example.taskscheduler.model.dto.UserDTO;
import com.example.taskscheduler.repositories.TaskRepository;
import com.example.taskscheduler.services.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private static final String IMAGE_DIRECTORY = "src/main/resources/static/images/";
    private final TaskRepository taskRepository;
    private final MappingServiceImpl mappingService;

    public TaskServiceImpl(TaskRepository taskRepository, MappingServiceImpl mappingService) {
        this.taskRepository = taskRepository;
        this.mappingService = mappingService;
    }

    @Override
    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(mappingService::mapTaskToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TaskDTO> findTaskById(Long taskId) {
        return taskRepository.findById(taskId).map(mappingService::mapTaskToDto);
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        return mappingService.mapTaskToDto(taskRepository.save(mappingService.mapDtoToTask(taskDTO)));
    }

    @Override
    public Optional<TaskDTO> addUsersToTask(Long taskId, List<UserDTO> userDTOs) {
        Optional<TaskDTO> task = taskRepository.findById(taskId).map(mappingService::mapTaskToDto);
        if (task.isPresent()){
            task.get().setAssignedUsers(userDTOs);
            taskRepository.save(mappingService.mapDtoToTask(task.get()));
        }
        return task;
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void uploadImage(MultipartFile image, Long taskId) throws IOException {
        Optional<TaskDTO> task = taskRepository.findById(taskId).map(mappingService::mapTaskToDto);

        if (!image.isEmpty() && task.isPresent()){
            String newFileName = "TaskID_" + taskId + "__" + image.getOriginalFilename();
            Path uploadPath = Path.of(IMAGE_DIRECTORY);
            Path filePath = uploadPath.resolve(newFileName);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            task.get().setImage(newFileName);
            taskRepository.save(mappingService.mapDtoToTask(task.get()));
        }
    }
    @Override
    public List<TaskDTO> findAllByBucketId(Long id) {
        return taskRepository.findAllByBucketId(id).stream().map(mappingService::mapTaskToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllByDueDate(LocalDate dueDate) {
        return taskRepository.findAllByDueDate(dueDate).stream().map(mappingService::mapTaskToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> filterTasks(Integer numberOfTasks, LocalDate startDate, LocalDate endDate, List<UserDTO> userDTOs) {
        List<TaskDTO> tasks= new ArrayList<>();
        for (UserDTO u : userDTOs){
            for (String p : Arrays.asList("Low", "Medium", "High", "Critical")){
                List<TaskDTO> fetchedTasks = taskRepository.findAllByUserAndPeriod(u.getId(), startDate, endDate, p).stream().map(mappingService::mapTaskToDto).toList();
                if (tasks.size() > numberOfTasks) {
                    tasks = tasks.subList(0, numberOfTasks);
                }
                tasks.addAll(fetchedTasks);
            }
        }
        Comparator<TaskDTO> priorityComparator = (t1, t2) -> {
            List<String> priorities = Arrays.asList("Low", "Medium", "High", "Critical");
            int index1 = priorities.indexOf(t1.getPriority());
            int index2 = priorities.indexOf(t2.getPriority());
            return Integer.compare(index1, index2);
        };

        tasks.sort(priorityComparator);

        return tasks;
    }
}
