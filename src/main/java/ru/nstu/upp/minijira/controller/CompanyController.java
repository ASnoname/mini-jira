package ru.nstu.upp.minijira.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nstu.upp.minijira.dto.AvailableExecutorsResponseDto;
import ru.nstu.upp.minijira.dto.CompanyDto;
import ru.nstu.upp.minijira.service.CompanyService;

import java.util.UUID;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private static final String USERS = "/{companyId}/users";

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<CompanyDto> create(@RequestBody CompanyDto companyDto) {
        return ResponseEntity.ok(companyService.create(companyDto));
    }

    @GetMapping(USERS)
    public ResponseEntity<AvailableExecutorsResponseDto>
    getAllUsersByCompanyId(@PathVariable("{companyId}") UUID companyId) {
        return ResponseEntity.ok(companyService.getAllUsersByCompanyId(companyId));
    }
}
