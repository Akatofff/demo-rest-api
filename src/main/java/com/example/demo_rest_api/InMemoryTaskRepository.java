package com.example.demo_rest_api;

import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final List<Task> tasks = new LinkedList<>() {{ //Not for concurrent access | TODO: replace LinkedList with Collections.synchronizedList?
        this.add(new Task("First task"));
        this.add(new Task("Second task"));
    }};

    @Override
    public List<Task> findAll() {
        return this.tasks; //Need to return modified copy of list
    }

    @Override
    public void save(Task task) {
        this.tasks.add(task);
    }

    @Override
    public void delete(Task task) {
        this.tasks.remove(task);
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return this.tasks.stream()
                .filter(task -> task.id().equals(id))
                .findFirst();
    }
}
