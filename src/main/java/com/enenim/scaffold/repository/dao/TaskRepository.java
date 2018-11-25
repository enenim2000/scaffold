package com.enenim.scaffold.repository.dao;

import com.enenim.scaffold.model.dao.Task;
import com.enenim.scaffold.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface TaskRepository extends BaseRepository<Task, Long> {
    @Query("select task from Task task where task.route = ?1")
    Optional<Task> findTaskByRoute(String taskRoute);

    @Query("select task from Task task where task.id in ?1")
    Set<Task> findTasksByIds (List<Long> ids);

    @Query("select task from Task task where task.route in ?1")
    Set<Task> findTasksByRoutes (List<String> routes);
}