package ru.nstu.upp.minijira.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nstu.upp.minijira.dto.CompanyDto;
import ru.nstu.upp.minijira.service.CompanyService;

@RestController
@RequestMapping("/v1/company")
public class CompanyController {

    private static final String BY_ID = "/{id}";

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(BY_ID)
    public ResponseEntity<CompanyDto> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(companyService.getById(id));
    }
}
