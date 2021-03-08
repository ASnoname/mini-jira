package ru.nstu.upp.minijira.service;

import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import ru.nstu.upp.minijira.dto.StateDto;
import ru.nstu.upp.minijira.dto.TaskDto;
import ru.nstu.upp.minijira.dto.TaskViewResponseDto;
import ru.nstu.upp.minijira.entity.Task;
import ru.nstu.upp.minijira.entity.TaskState;
import ru.nstu.upp.minijira.entity.User;
import ru.nstu.upp.minijira.repository.TaskRepository;
import ru.nstu.upp.minijira.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class TelegramService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final MapperFacade mapperFacade;

    public TelegramService(TaskRepository taskRepository,
                           UserRepository userRepository,
                           MapperFacade mapperFacade) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.mapperFacade = mapperFacade;
    }

    public TaskViewResponseDto view(String chatId) {
        User user = userRepository.findByTelegramChatId(chatId);
        if (user == null) {
            return null;
        }
        List<Task> tasks = taskRepository.findAllByReporterOrExecutor(user, user);
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
        return mapperFacade.map(entity, TaskViewResponseDto.TaskInfo.class);
    }

    private TaskDto mapToTaskDto(Task entity) {
        return mapperFacade.map(entity, TaskDto.class);
    }
}
