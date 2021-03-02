package ru.nstu.upp.minijira.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nstu.upp.minijira.entity.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    Company getById(long id);
}
