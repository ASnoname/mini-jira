package ru.nstu.upp.minijira.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nstu.upp.minijira.entity.Task;

import java.util.UUID;

@Repository
public interface TaskRepository extends CrudRepository<Task, UUID> {

}
