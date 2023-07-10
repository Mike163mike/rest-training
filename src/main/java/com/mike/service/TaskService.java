package com.mike.service;

import com.mike.model.Task;
import com.mike.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskService {

    TaskRepository taskRepository;

    public Task createTask(String details) {
        if (!validateDetails(details)) {
            throw new IllegalArgumentException("Invalid data. Check your message.");
        }
        var task = new Task();
        task.setId(UUID.randomUUID());
        task.setDetails(details);
        task.setCompleted(false);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public  Task getTaskById(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public void deleteTaskById(UUID id) {
        taskRepository.deleteById(id);
    }

    private boolean validateDetails(String details) {
        if (details == null) {
            return false;
        }
        if (details.isBlank()) {
            return false;
        }
        return details.length() >= 5;
    }
}
