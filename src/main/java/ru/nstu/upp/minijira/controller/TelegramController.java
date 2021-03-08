package ru.nstu.upp.minijira.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nstu.upp.minijira.dto.StateDto;
import ru.nstu.upp.minijira.dto.TaskDto;
import ru.nstu.upp.minijira.dto.TaskViewResponseDto;
import ru.nstu.upp.minijira.service.TelegramService;

import java.util.UUID;

@RestController
@RequestMapping("/tg")
public class TelegramController {

    private static final String TASK_VIEW = "/view/{chatId}";
    private static final String TASK_BY_ID = "/task/{taskId}/{chatId}";
    private static final String TASK_CHANGE_STATE = "/task/{taskId}/{chatId}";

    private final TelegramService telegramService;

    public TelegramController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @GetMapping(TASK_VIEW)
    public ResponseEntity<TaskViewResponseDto> view(@PathVariable("chatId") String chatId) {
        TaskViewResponseDto view = telegramService.view(chatId);
        if (view == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(view);
        }
    }

    @GetMapping(TASK_BY_ID)
    public ResponseEntity<TaskDto> taskById(@PathVariable("taskId") UUID id, @PathVariable("chatId") String chatId) {
        TaskDto taskDto = telegramService.taskById(id, chatId);
        if (taskDto == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(taskDto);
        }
    }

    @PostMapping(TASK_CHANGE_STATE)
    public ResponseEntity<Object> changeState(@PathVariable("taskId") UUID id,
                                              @PathVariable("chatId") String chatId,
                                              @RequestBody StateDto stateDto) {
        Object result = telegramService.changeState(id, chatId, stateDto);
        if (result == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
