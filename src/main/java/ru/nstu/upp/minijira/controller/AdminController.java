package ru.nstu.upp.minijira.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nstu.upp.minijira.dto.CompanyDto;
import ru.nstu.upp.minijira.service.CompanyService;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final String BASE_COMPANY = "/company";
    private static final String BY_ID = BASE_COMPANY + "/{id}";

    private final CompanyService companyService;

    public AdminController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(BY_ID)
    public ResponseEntity<CompanyDto> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(companyService.getById(id));
    }
}
