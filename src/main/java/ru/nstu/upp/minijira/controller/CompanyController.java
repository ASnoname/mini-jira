package ru.nstu.upp.minijira.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nstu.upp.minijira.dto.CompanyDto;
import ru.nstu.upp.minijira.service.CompanyService;

import java.util.UUID;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private static final String BY_ID = "/{id}";

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<CompanyDto> create(@RequestBody CompanyDto companyDto) {
        return ResponseEntity.ok(companyService.create(companyDto));
    }

    @GetMapping(BY_ID)
    public ResponseEntity<CompanyDto> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(companyService.getById(id));
    }
}
