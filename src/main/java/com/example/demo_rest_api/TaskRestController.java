package com.example.demo_rest_api;

import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {

    private final TaskRepository taskRepository;

    private final MessageSource messageSource;

    public TaskRestController(TaskRepository taskRepository,
                              MessageSource messageSource) {
        this.taskRepository = taskRepository;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ResponseEntity<List<Task>> handleGetAllTasks() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.taskRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> handleCreateNewTask(
            @RequestBody NewTaskPayload payload,
            UriComponentsBuilder uriComponentsBuilder,
            Locale locale) {
        if (payload.details() == null || payload.details().isBlank()) {
            final String message = this.messageSource
                    .getMessage("task.create.details.errors.not_set", new Object[0], locale);
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ErrorsPresentation(List.of(message)));
        }
        var task = new Task(payload.details());
        this.taskRepository.save(task);
        return ResponseEntity.created(uriComponentsBuilder
                .path("/api/tasks/{taskId}")
                .build(Map.of("taskId", task.id())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(task);
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> handleFindTask(@PathVariable("id") UUID id) {
        return ResponseEntity.of(this.taskRepository.findById(id));
    }

}
