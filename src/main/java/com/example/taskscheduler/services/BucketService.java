package com.example.taskscheduler.services;

import com.example.taskscheduler.model.dto.BucketDTO;
import com.example.taskscheduler.model.entities.Bucket;

import java.util.List;
import java.util.Optional;

public interface BucketService {
    List<BucketDTO> findAll();
    Optional<BucketDTO> findById(Long id);
    BucketDTO createBucket(BucketDTO bucketDTO);
}
