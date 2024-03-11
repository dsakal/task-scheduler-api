package com.example.taskscheduler.controllers;

import com.example.taskscheduler.model.dto.BucketDTO;
import com.example.taskscheduler.services.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BucketController {

    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping(path = "/buckets")
    public ResponseEntity<List<BucketDTO>> findAllBuckets(){
        return new ResponseEntity<>(bucketService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/buckets/{id}")
    public ResponseEntity<BucketDTO> findBucketById(@PathVariable Long id){
        return bucketService.findById(id)
                .map(bucketDTO -> new ResponseEntity<>(bucketDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/buckets")
    public ResponseEntity<BucketDTO> createBucket(@RequestBody BucketDTO bucketDTO){
        return new ResponseEntity<>(bucketService.createBucket(bucketDTO), HttpStatus.CREATED);
    }
}
