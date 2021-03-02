package ru.nstu.upp.minijira.service;

import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import ru.nstu.upp.minijira.dto.CompanyDto;
import ru.nstu.upp.minijira.entity.Company;
import ru.nstu.upp.minijira.repository.CompanyRepository;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;
    private MapperFacade mapperFacade;

    public CompanyService(CompanyRepository companyRepository, MapperFacade mapperFacade) {
        this.companyRepository = companyRepository;
        this.mapperFacade = mapperFacade;
    }

    public CompanyDto getById(long id) {
        Company company = companyRepository.getById(id);
        return mapperFacade.map(company, CompanyDto.class);
    }
}
