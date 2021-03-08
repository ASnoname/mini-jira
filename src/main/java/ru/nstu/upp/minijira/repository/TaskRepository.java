package ru.nstu.upp.minijira.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nstu.upp.minijira.entity.Task;
import ru.nstu.upp.minijira.entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends CrudRepository<Task, UUID> {
    List<Task> findAllByReporterOrExecutor(User reporter, User executor);
    Task getById(UUID id);
}
