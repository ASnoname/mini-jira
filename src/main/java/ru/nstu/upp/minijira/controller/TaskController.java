package ru.nstu.upp.minijira.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nstu.upp.minijira.dto.*;
import ru.nstu.upp.minijira.service.TaskService;

import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    private static final String BY_ID = "/{taskId}";
    private static final String CREATE = "/reporter/{reporterId}";
    private static final String UPDATE = "/{taskId}";
    private static final String VIEW = "/view";

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(BY_ID)
    public ResponseEntity<TaskDto> getById(@PathVariable("taskId") UUID taskId) {
        return ResponseEntity.ok(taskService.getById(taskId));
    }

    @PostMapping(CREATE)
    public ResponseEntity<TaskDto> create(@PathVariable("reporterId") UUID reporterId,
                                          @RequestBody CreateTaskRequestDto dto) {
        return ResponseEntity.ok(taskService.create(reporterId, dto));
    }

    @PostMapping(UPDATE)
    public ResponseEntity<TaskDto> update(@PathVariable("taskId") UUID taskId,
                                          @RequestBody UpdateTaskDto dto) {
        return ResponseEntity.ok(taskService.update(taskId, dto));
    }

    @PostMapping(VIEW)
    public ResponseEntity<TaskViewResponse> view(@RequestBody TaskViewFilter filter) {
        return ResponseEntity.ok(taskService.view(filter));
    }
}