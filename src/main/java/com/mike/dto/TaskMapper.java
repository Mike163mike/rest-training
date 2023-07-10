package com.mike.dto;

import com.mike.model.Task;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface TaskMapper {

    Task map(TaskDto taskDto);
    TaskDto map(Task task);
    default List<TaskDto> map(@NotNull List<Task> taskList) {
        return taskList.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

}
