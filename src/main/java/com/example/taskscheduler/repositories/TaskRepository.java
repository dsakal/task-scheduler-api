package com.example.taskscheduler.repositories;

import com.example.taskscheduler.model.entities.Task;
import com.example.taskscheduler.model.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.bucket.id = ?1")
    List<Task> findAllByBucketId(Long id);

    @Query("SELECT t FROM Task t WHERE t.dueDate = ?1")
    List<Task> findAllByDueDate(LocalDate dueDate);

    @Query("SELECT t FROM Task t JOIN t.assignedUsers u WHERE u.id = ?1 AND t.dueDate >= ?2 AND t.dueDate <= ?3 AND t.priority = ?4")
    List<Task> findAllByUserAndPeriod(Long userId, LocalDate fromDate, LocalDate toDate, String priority);


}
