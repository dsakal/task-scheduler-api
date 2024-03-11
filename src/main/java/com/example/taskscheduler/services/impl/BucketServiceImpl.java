package com.example.taskscheduler.services.impl;

import com.example.taskscheduler.model.entities.Bucket;
import com.example.taskscheduler.model.dto.BucketDTO;
import com.example.taskscheduler.repositories.BucketRepository;
import com.example.taskscheduler.repositories.TaskRepository;
import com.example.taskscheduler.services.BucketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService {
    private final BucketRepository bucketRepository;
    private final TaskRepository taskRepository;
    private final MappingServiceImpl mappingService;

    public BucketServiceImpl(BucketRepository bucketRepository, TaskRepository taskRepository, MappingServiceImpl mappingService) {
        this.bucketRepository = bucketRepository;
        this.taskRepository = taskRepository;
        this.mappingService = mappingService;
    }

    @Override
    public List<BucketDTO> findAll() {
        return bucketRepository.findAll().stream()
                .map(bucket -> {
                    BucketDTO dto = mappingService.mapBucketToDto(bucket);
                    dto.setTasks(taskRepository.findAllByBucketId(dto.getId()).stream().map( task -> mappingService.mapTaskToDto(task)).toList());
                    return dto;
                })
                .toList();
    }

    @Override
    public Optional<BucketDTO> findById(Long id) {
        Optional<Bucket> bucket= bucketRepository.findById(id);
        if (bucket.isPresent()){
            return Optional.of(mappingService.mapBucketToDto(bucket.get()));
        }
        return Optional.empty();
    }

    @Override
    public BucketDTO createBucket(BucketDTO bucketDto) {
        return mappingService.mapBucketToDto(bucketRepository.save(mappingService.mapDtoToBucket(bucketDto)));
    }
}
