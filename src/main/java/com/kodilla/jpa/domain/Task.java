package com.kodilla.jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "tasks", fetch = FetchType.LAZY)
    private final List<Person> persons = new ArrayList<>();

    @OneToMany(
            targetEntity = Subtask.class,
            mappedBy = "task",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private final List<Subtask> subtasks = new ArrayList<>();

    private String name;
    private String status;

    public Task() {
    }

    public Task(Long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
