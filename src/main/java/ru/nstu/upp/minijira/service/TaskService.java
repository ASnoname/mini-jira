package ru.nstu.upp.minijira.service;

import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;
import ru.nstu.upp.minijira.dto.*;
import ru.nstu.upp.minijira.entity.Task;
import ru.nstu.upp.minijira.entity.TaskState;
import ru.nstu.upp.minijira.entity.User;
import ru.nstu.upp.minijira.exception.UserNotFoundException;
import ru.nstu.upp.minijira.repository.TaskRepository;
import ru.nstu.upp.minijira.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final MapperFactory mapperFactory;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, MapperFactory mapperFactory) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.mapperFactory = mapperFactory;
    }

    public TaskDto getById(UUID id) {
        Task task = taskRepository.getById(id);
        if (task == null) {
            return null;
        }
        return mapToTaskDto(task);
    }

    public TaskDto create(UUID reporterId, CreateTaskRequestDto dto) {
        Optional<User> reporter = userRepository.findById(reporterId);
        User executor = userRepository.findByLogin(dto.getExecutorLogin());
        if (reporter.isEmpty() || executor == null) {
            throw new UserNotFoundException();
        }
        Task task = map(dto);
        task.setCreateDate(new Date());
        task.setExecutor(executor);
        task.setReporter(reporter.get());
        task.setState(TaskState.CREATED);
        return mapToTaskDto(taskRepository.save(task));
    }

    public TaskDto update(UUID taskId, UpdateTaskDto dto) {
        Task task = taskRepository.getById(taskId);
        if (task == null) {
            return null;
        }
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setState(TaskState.valueOf(dto.getState()));
        task.setDeadline(dto.getDeadline());
        if (dto.getExecutorId() != null) {
            User executor = userRepository.getById(dto.getExecutorId());
            if (executor != null) {
                task.setExecutor(executor);
            } else {
                throw new UserNotFoundException();
            }
        }
        return mapToTaskDto(task);
    }

    public TaskViewResponse view(TaskViewFilter filter) {
        return new TaskViewResponse(map(taskRepository.getAllByStateNotLike(TaskState.DELETED)));
    }

    private TaskDto mapToTaskDto(Task entity) {
        return mapperFactory.getMapperFacade().map(entity, TaskDto.class);
    }

    private Task map(CreateTaskRequestDto request) {
        return mapperFactory.getMapperFacade().map(request, Task.class);
    }

    private List<TaskDto> map(List<Task> tasks) {
        return mapperFactory.getMapperFacade().mapAsList(tasks, TaskDto.class);
    }
}
