package com.mike.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "task")
@RequiredArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    private UUID id;
    private String details;
    private boolean completed;
}
