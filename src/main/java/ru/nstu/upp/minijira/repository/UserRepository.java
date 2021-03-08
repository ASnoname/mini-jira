package ru.nstu.upp.minijira.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nstu.upp.minijira.entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    User findByLogin(String login);
    boolean existsByLogin(String login);
    List<User> findAllByCompanyId(UUID companyId);
    User findByTelegramChatId(String chatId);
    boolean existsByTelegramChatId(String chatId);
}
