package com.kodilla.jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "subtasks", fetch = FetchType.LAZY)
    private final List<Person> persons = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    private String name;
    private String status;

    public Subtask() {
    }

    public Subtask(Task task, String name, String status) {
        this.task = task;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public Task getTask() {
        return task;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
