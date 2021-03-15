package ru.nstu.upp.minijira.service;

import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;
import ru.nstu.upp.minijira.dto.*;
import ru.nstu.upp.minijira.entity.Task;
import ru.nstu.upp.minijira.entity.TaskState;
import ru.nstu.upp.minijira.entity.User;
import ru.nstu.upp.minijira.entity.UserState;
import ru.nstu.upp.minijira.exception.UserNotFoundException;
import ru.nstu.upp.minijira.repository.TaskRepository;
import ru.nstu.upp.minijira.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class TelegramService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final MapperFactory mapperFactory;

    public TelegramService(TaskRepository taskRepository,
                           UserRepository userRepository,
                           MapperFactory mapperFactory) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.mapperFactory = mapperFactory;
    }

    public TaskViewResponseDto view(String chatId) {
        User user = userRepository.findByTelegramChatId(chatId);
        if (user == null) {
            return null;
        }
        List<Task> tasks = taskRepository.findAllByReporterOrExecutor(user, user)
                .stream()
                .filter(task -> !List.of(TaskState.CLOSED, TaskState.DELETED).contains(task.getState()))
                .collect(Collectors.toList());
        return new TaskViewResponseDto(
                createTaskInfo(tasks, task -> task.getReporter().equals(user)),
                createTaskInfo(tasks, task -> task.getExecutor().equals(user))
        );
    }

    public TaskDto taskById(UUID id, String chatId) {
        Task task = taskRepository.getById(id);
        if (isNotValid(task, chatId)) {
            return null;
        }
        TaskDto taskDto = mapToTaskDto(task);
        taskDto.setIsExecutor(task.getExecutor().getTelegramChatId().equals(chatId));
        taskDto.setIsReporter(task.getReporter().getTelegramChatId().equals(chatId));
        return taskDto;
    }

    public Object changeState(UUID id, String chatId, StateDto stateDto) {
        Task task = taskRepository.getById(id);
        if (isNotValid(task, chatId)) {
            return null;
        }
        TaskState newState = TaskState.valueOf(stateDto.getState());
        if (hasAuthority(chatId, task, newState)) {
            task.setState(newState);
            return taskRepository.save(task);
        } else {
            return null;
        }
    }

    public AvailableExecutorsResponseDto getAvailableExecutors(String chatId) {
        User user = userRepository.findByTelegramChatId(chatId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        List<User> activeUsers = user.getCompany().getUsers().stream()
                .filter(u -> UserState.ACTIVE == u.getState())
                .collect(Collectors.toList());
        List<AvailableExecutorsResponseDto.Executor> executors = mapToExecutors(activeUsers);
        return new AvailableExecutorsResponseDto(executors);
    }

    public TaskDto createTask(String chatId, CreateTaskRequestDto request) {
        User reporter = userRepository.findByTelegramChatId(chatId);
        User executor = userRepository.findByLogin(request.getExecutorLogin());
        if (reporter == null || executor == null) {
            throw new UserNotFoundException();
        }
        Task task = map(request);
        task.setCreateDate(new Date());
        task.setExecutor(executor);
        task.setReporter(reporter);
        task.setState(TaskState.CREATED);
        return mapToTaskDto(taskRepository.save(task));
    }

    private boolean hasAuthority(String chatId, Task task, TaskState newState) {
        boolean isReporter = task.getReporter().getTelegramChatId().equals(chatId);
        boolean isExecutor = task.getExecutor().getTelegramChatId().equals(chatId);
        return task.getState().hasAuthority(newState, isExecutor, isReporter);
    }

    private boolean isNotValid(Task task, String chatId) {
        boolean userIsExist = userRepository.existsByTelegramChatId(chatId);
        return task == null || !userIsExist;
    }

    private List<TaskViewResponseDto.TaskInfo> createTaskInfo(List<Task> tasks, Predicate<Task> predicate) {
        return tasks.stream()
                .filter(predicate)
                .map(this::mapToTaskInfo)
                .collect(Collectors.toList());
    }

    private TaskViewResponseDto.TaskInfo mapToTaskInfo(Task entity) {
        return mapperFactory.getMapperFacade().map(entity, TaskViewResponseDto.TaskInfo.class);
    }

    private TaskDto mapToTaskDto(Task entity) {
        return mapperFactory.getMapperFacade().map(entity, TaskDto.class);
    }

    private List<AvailableExecutorsResponseDto.Executor> mapToExecutors(List<User> users) {
        mapperFactory.classMap(User.class, AvailableExecutorsResponseDto.Executor.class)
                .field("login", "executorLogin")
                .byDefault().register();

        return mapperFactory.getMapperFacade().mapAsList(users, AvailableExecutorsResponseDto.Executor.class);
    }

    private Task map(CreateTaskRequestDto request) {
        return mapperFactory.getMapperFacade().map(request, Task.class);
    }
}
