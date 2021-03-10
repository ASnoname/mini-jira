package ru.nstu.upp.minijira.service;

import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;
import ru.nstu.upp.minijira.dto.CompanyDto;
import ru.nstu.upp.minijira.entity.Company;
import ru.nstu.upp.minijira.repository.CompanyRepository;

import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final MapperFactory mapperFactory;

    public CompanyService(CompanyRepository companyRepository, MapperFactory mapperFactory) {
        this.companyRepository = companyRepository;
        this.mapperFactory = mapperFactory;
    }

    public CompanyDto create(CompanyDto companyDto) {
        Company company = map(companyDto);
        company.setInviteCode(UUID.randomUUID().toString());
        Company save = companyRepository.save(company);
        return map(save);
    }

    public CompanyDto getById(UUID id) {
        Company company = companyRepository.getById(id);
        return map(company);
    }

    private Company map(CompanyDto dto) {
        return mapperFactory.getMapperFacade().map(dto, Company.class);
    }

    private CompanyDto map(Company entity) {
        return mapperFactory.getMapperFacade().map(entity, CompanyDto.class);
    }
}
