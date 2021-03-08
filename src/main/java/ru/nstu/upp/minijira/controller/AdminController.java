package ru.nstu.upp.minijira.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nstu.upp.minijira.dto.CompanyDto;
import ru.nstu.upp.minijira.dto.UserDto;
import ru.nstu.upp.minijira.entity.UserState;
import ru.nstu.upp.minijira.service.CompanyService;
import ru.nstu.upp.minijira.service.UserService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final String BASE_COMPANY = "/company";
    private static final String BY_ID = BASE_COMPANY + "/{id}";
    private static final String USER_LIST = BASE_COMPANY + "/{company_id}/users";
    private static final String USER_ACTIVATE = USER_LIST + "/{user_id}/activate";
    private static final String USER_ARCHIVE = USER_LIST + "/{user_id}/archive";
    private static final String USER_SET_ADMIN = USER_LIST + "/{user_id}/set-admin";
    private static final String USER_REMOVE_ADMIN = USER_LIST + "/{user_id}/remove-admin";

    private final CompanyService companyService;
    private final UserService userService;

    public AdminController(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    @GetMapping(BY_ID)
    public ResponseEntity<CompanyDto> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(companyService.getById(id));
    }

    @GetMapping(USER_LIST)
    public ResponseEntity<List<UserDto>> getCompanyUsers(@PathVariable("company_id") UUID companyId) {
        return ResponseEntity.ok(userService.getCompanyUsers(companyId)
                .stream().map(userService::map).collect(Collectors.toList()));
    }

    @PostMapping(USER_ACTIVATE)
    public ResponseEntity<UserDto> activateUser(
            @PathVariable("company_id") UUID companyId, @PathVariable("user_id") UUID userId) {
        // TODO: по-хорошему проверять, что этот пользователь принадлежит компании
        return ResponseEntity.ok(userService.setUserState(userId, UserState.ACTIVE));
    }

    @PostMapping(USER_ARCHIVE)
    public ResponseEntity<UserDto> archiveUser(
            @PathVariable("company_id") UUID companyId, @PathVariable("user_id") UUID userId) {
        // TODO: по-хорошему проверять, что этот пользователь принадлежит компании
        return ResponseEntity.ok(userService.setUserState(userId, UserState.ARCHIVE));
    }

    @PostMapping(USER_SET_ADMIN)
    public ResponseEntity<UserDto> setAdmin(
            @PathVariable("company_id") UUID companyId, @PathVariable("user_id") UUID userId) {
        // TODO: по-хорошему проверять, что этот пользователь принадлежит компании
        return ResponseEntity.ok(userService.setAdmin(userId));
    }

    @PostMapping(USER_REMOVE_ADMIN)
    public ResponseEntity<UserDto> removeAdmin(
            @PathVariable("company_id") UUID companyId, @PathVariable("user_id") UUID userId) {
        // TODO: по-хорошему проверять, что этот пользователь принадлежит компании
        return ResponseEntity.ok(userService.removeAdmin(userId));
    }
}
