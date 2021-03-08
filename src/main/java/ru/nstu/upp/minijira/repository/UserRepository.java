package ru.nstu.upp.minijira.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.nstu.upp.minijira.entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, UUID> {
    User findByLogin(String login);
    boolean existsByLogin(String login);

//    Page<User> findAll(Pageable page/*UUID company*/);
    List<User> findAllByCompanyId(UUID companyId);
}
