package com.example.taskscheduler.repositories;

import com.example.taskscheduler.model.entities.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, Long>{
}
