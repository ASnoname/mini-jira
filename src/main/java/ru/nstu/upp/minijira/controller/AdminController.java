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

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final String BASE_COMPANY = "/company";
    private static final String BY_ID = BASE_COMPANY + "/{id}";
    private static final String USER_LIST = "/users/{company_id}";
    private static final String USER_ACTIVATE = BASE_COMPANY + "/{user_id}/activate";
    private static final String USER_ARCHIVE = BASE_COMPANY + "/{user_id}/archive";
    private static final String USER_REMOVE = BASE_COMPANY + "/{user_id}/remove";
    private static final String USER_SET_ADMIN = BASE_COMPANY + "/{user_id}/set-admin";
    private static final String USER_REMOVE_ADMIN = BASE_COMPANY + "/{user_id}/remove-admin";

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
    public ResponseEntity<List<UserDto>> getCompanyUsers(
            @PathVariable("company_id") UUID companyId,
            @RequestParam(value = "state", required = false) UserState userState) {
        return ResponseEntity.ok(userService.getCompanyUsers(companyId, userState));
    }

    @PostMapping(USER_ACTIVATE)
    public ResponseEntity<UserDto> activateUser(@PathVariable("user_id") UUID userId) {
        return ResponseEntity.ok(userService.setUserState(userId, UserState.ACTIVE));
    }

    @PostMapping(USER_ARCHIVE)
    public ResponseEntity<UserDto> archiveUser(@PathVariable("user_id") UUID userId) {
        return ResponseEntity.ok(userService.setUserState(userId, UserState.ARCHIVE));
    }

    @PostMapping(USER_SET_ADMIN)
    public ResponseEntity<UserDto> setAdmin(@PathVariable("user_id") UUID userId) {
        return ResponseEntity.ok(userService.setAdmin(userId));
    }

    @PostMapping(USER_REMOVE_ADMIN)
    public ResponseEntity<UserDto> removeAdmin(@PathVariable("user_id") UUID userId) {
        return ResponseEntity.ok(userService.removeAdmin(userId));
    }

    @DeleteMapping(USER_REMOVE)
    public ResponseEntity removeUser(@PathVariable("user_id") UUID userId) {
        userService.remove(userId);
        return ResponseEntity.ok().build();
    }

}
