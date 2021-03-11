package ru.nstu.upp.minijira.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nstu.upp.minijira.entity.User;
import ru.nstu.upp.minijira.entity.UserState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    User findByLogin(String login);
    boolean existsByLogin(String login);
    List<User> findAllByCompanyId(UUID companyId);
    List<User> findAllByCompanyIdAndState(UUID companyId, UserState state);
    User findByTelegramChatId(String chatId);
    boolean existsByTelegramChatId(String chatId);
}
