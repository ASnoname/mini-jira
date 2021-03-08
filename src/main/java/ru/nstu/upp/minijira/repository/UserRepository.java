package ru.nstu.upp.minijira.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.nstu.upp.minijira.entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, UUID> {
    List<User> findAllByCompanyId(UUID companyId);
    User findByLogin(String login);
    boolean existsByLogin(String login);
    User findByTelegramChatId(String chatId);
    boolean existsByTelegramChatId(String chatId);
}
