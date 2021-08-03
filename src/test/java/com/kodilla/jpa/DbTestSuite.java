package com.kodilla.jpa;

import com.kodilla.jpa.domain.Person;
import com.kodilla.jpa.domain.Subtask;
import com.kodilla.jpa.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class DbTestSuite {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void test() {
        List<Long> savedPersonIds = insertTestData();
        EntityManager em = emf.createEntityManager();

        TypedQuery<Person> query = em.createQuery(
                "from Person "
                        + " where id in (" + idList(savedPersonIds) + ")",
                Person.class);

        EntityGraph<Person> eg = em.createEntityGraph(Person.class);
        eg.addSubgraph("tasks");
        query.setHint("javax.persistence.fetchgraph", eg);

        List<Person> persons = query.getResultList();

        for (Person person : persons) {
            System.out.println(person);
            for (Task task : person.getTasks()) {
                System.out.println(task);
            }
        }
    }

    private String idList(List<Long> ids) {
        return ids.stream()
                .map(n -> "" + n)
                .collect(Collectors.joining(","));
    }

    private List<Long> insertTestData() {
        Person person1 = new Person(null, "Ben", "Stiller");
        Person person2 = new Person(null, "Adam", "Sandler");

        Task task1 = new Task(null, "Task1", "TO DO");
        Task task2 = new Task(null, "Task2", "DONE");

        Subtask subtask1 = new Subtask(task1, "Subtask1", "TO DO");
        Subtask subtask2 = new Subtask(task1, "Subtask2", "TO DO");
        Subtask subtask3 = new Subtask(task2, "Subtask3", "DONE");
        Subtask subtask4 = new Subtask(task2, "Subtask4", "DONE");

        task1.getSubtasks().add(subtask1);
        task1.getSubtasks().add(subtask2);

        task2.getSubtasks().add(subtask3);
        task2.getSubtasks().add(subtask4);

        person1.getTasks().add(task1);

        person1.getSubtasks().add(subtask1);
        person1.getSubtasks().add(subtask2);

        person2.getTasks().add(task2);

        person2.getSubtasks().add(subtask3);
        person2.getSubtasks().add(subtask4);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(person1);
        em.persist(person2);
        em.persist(task1);
        em.persist(task2);
        em.persist(subtask1);
        em.persist(subtask2);
        em.persist(subtask3);
        em.persist(subtask4);

        em.flush();
        em.getTransaction().commit();
        em.close();

        return List.of(person1.getId(), person2.getId());
    }
}
