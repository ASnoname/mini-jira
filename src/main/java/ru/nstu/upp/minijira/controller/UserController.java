package ru.nstu.upp.minijira.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nstu.upp.minijira.dto.CompanyDto;
import ru.nstu.upp.minijira.dto.UserDto;
import ru.nstu.upp.minijira.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final String BY_ID = "/{id}";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(BY_ID)
    public ResponseEntity<UserDto> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userService.getById(id));
    }
}
