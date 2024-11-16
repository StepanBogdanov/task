package com.test.task.repository;

import com.test.task.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepository extends JpaRepository<Task, long>, JpaSpecificationExecutor<Task> {
}
