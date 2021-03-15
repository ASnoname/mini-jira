package ru.nstu.upp.minijira.service;

import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;
import ru.nstu.upp.minijira.dto.AvailableExecutorsResponseDto;
import ru.nstu.upp.minijira.dto.CompanyDto;
import ru.nstu.upp.minijira.entity.Company;
import ru.nstu.upp.minijira.entity.User;
import ru.nstu.upp.minijira.entity.UserState;
import ru.nstu.upp.minijira.exception.CompanyNotFoundException;
import ru.nstu.upp.minijira.repository.CompanyRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public AvailableExecutorsResponseDto getAllUsersByCompanyId(UUID companyId) {
        Company company = companyRepository.getById(companyId);
        if (company == null) {
            throw new CompanyNotFoundException();
        }
        List<User> activeUsers = company.getUsers().stream()
                .filter(u -> UserState.ACTIVE == u.getState())
                .collect(Collectors.toList());
        List<AvailableExecutorsResponseDto.Executor> executors = mapToExecutors(activeUsers);
        return new AvailableExecutorsResponseDto(executors);
    }

    private Company map(CompanyDto dto) {
        return mapperFactory.getMapperFacade().map(dto, Company.class);
    }

    private CompanyDto map(Company entity) {
        return mapperFactory.getMapperFacade().map(entity, CompanyDto.class);
    }

    private List<AvailableExecutorsResponseDto.Executor> mapToExecutors(List<User> users) {
        mapperFactory.classMap(User.class, AvailableExecutorsResponseDto.Executor.class)
                .field("login", "executorLogin")
                .byDefault().register();

        return mapperFactory.getMapperFacade().mapAsList(users, AvailableExecutorsResponseDto.Executor.class);
    }
}
