package com.mike.controller;

import com.mike.ErrorsList;
import com.mike.dto.TaskDto;
import com.mike.dto.TaskMapper;
import com.mike.model.Task;
import com.mike.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/task")
public class TaskRestController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final MessageSource messageSource;

    @GetMapping
    public ResponseEntity<List<TaskDto>> handleGetAllTasks() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskMapper.map(taskService.getAllTasks()));
    }

    @PostMapping
    public ResponseEntity<?> handleCreateNewTask(@RequestBody TaskDto taskDto,
                                                 UriComponentsBuilder uriComponentsBuilder,
                                                 Locale locale) {
        Task task = null;
        try {
            task = taskService.createTask(taskDto.details());
        } catch (IllegalArgumentException e) {
            var message = messageSource.getMessage("tasks.create.details.errors.not_set",
                    new Object[0], locale);
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ErrorsList(List.of(message)));
        }
        return ResponseEntity.created(uriComponentsBuilder.path("/api/task/{taskId}")
                        .build(Map.of("taskId", task.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> handleFindTask(@PathVariable("id") UUID id,
                                            Locale locale) {
        Task task = null;
        try {
            task = taskService.getTaskById(id);
        } catch (NoSuchElementException e) {
            var message = messageSource.getMessage("tasks.not.found",
                    new Object[0], locale);
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ErrorsList(List.of(message)));
        }
        return ResponseEntity.ok(taskMapper.map(task));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> HandleDeleteTaskById(@PathVariable("id") UUID id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.ok("Task with id = " + id + " was deleted successfully.");
    }
}
