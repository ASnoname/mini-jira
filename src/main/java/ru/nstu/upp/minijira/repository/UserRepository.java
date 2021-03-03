package ru.nstu.upp.minijira.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nstu.upp.minijira.entity.User;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

}
