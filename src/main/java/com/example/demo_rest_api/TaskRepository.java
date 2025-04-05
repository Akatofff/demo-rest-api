package com.example.demo_rest_api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {

    List<Task> findAll();

    void save(Task task);

    void delete(Task task); //TODO: Add deleteMapping endpoint | Need to remove tasks by Id (not description)

    Optional<Task> findById(UUID id);
}
