package com.example.taskscheduler.controllers;

import com.example.taskscheduler.model.dto.*;
import com.example.taskscheduler.services.BucketService;
import com.example.taskscheduler.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
public class TaskController {
    Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;
    private final BucketService bucketService;

    public TaskController(TaskService taskService, BucketService bucketService) {
        this.taskService = taskService;
        this.bucketService = bucketService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> findAllTasks(){
        return new ResponseEntity<>(taskService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/tasks/filter/{numberOfTasks}")
    public List<TaskDTO> getFilteredTasks(
            @PathVariable Integer numberOfTasks,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestBody List<UserDTO> users) {

        return taskService.filterTasks(numberOfTasks, startDate, endDate, users);
    }
    /*@PostMapping("/tasks")
    public ResponseEntity<TaskDTO> createTask(@ModelAttribute TaskDTO taskDTO, @RequestParam("image") MultipartFile file) {
        TaskDTO savedTask = taskService.createTask(taskDTO);
        try {
            String newFileName = "TaskID:" + savedTask.getId() + "__" + file.getOriginalFilename();
            savedTask.setImage(newFileName);
            taskService.uploadImage(file, newFileName);
        }catch (IOException e){
            logger.error("Failed to upload image!", e);
        }
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }*/

    @PostMapping("/tasks")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        TaskDTO savedTask = taskService.createTask(taskDTO);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PutMapping("/tasks/{taskId}/images")
    public ResponseEntity<String> uploadImage(@PathVariable Long taskId, @RequestParam("image") MultipartFile imageFile){
        try {
            if (!Objects.requireNonNull(imageFile.getContentType()).startsWith("image/")) {
                return new ResponseEntity<>("Only image files are allowed!", HttpStatus.BAD_REQUEST);
            }
            String newFileName = "TaskID_" + taskId.toString() + "__" + imageFile.getOriginalFilename();
            taskService.uploadImage(imageFile, newFileName);
            return new ResponseEntity<>("Image upload successful!", HttpStatus.OK);
        }catch (IOException e){
            logger.error("Failed to upload image!", e);
            return new ResponseEntity<>("Image upload failed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/buckets/{bucketId}/tasks")
    public ResponseEntity<TaskDTO> insertTaskIntoBucket(@PathVariable Long bucketId, @RequestBody TaskDTO taskDTO) {
        if (bucketService.findById(bucketId).isPresent()){
            taskDTO.setBucket(bucketService.findById(bucketId).get());
        }
        return new ResponseEntity<>(taskService.createTask(taskDTO), HttpStatus.OK);
    }

    @PutMapping("/tasks/{taskId}/users")
    public ResponseEntity<TaskDTO> addUsersToTask(@PathVariable Long taskId, @RequestBody List<UserDTO> userDTOs) {
        return new ResponseEntity<>(taskService.addUsersToTask(taskId, userDTOs), HttpStatus.OK);
    }
    @DeleteMapping("/tasks/{taskId}")
    public void delete(@PathVariable Long taskId){
        taskService.deleteById(taskId);
    }
}
