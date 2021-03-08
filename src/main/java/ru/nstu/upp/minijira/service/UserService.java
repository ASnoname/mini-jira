package ru.nstu.upp.minijira.service;

import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import ru.nstu.upp.minijira.dto.UserDto;
import ru.nstu.upp.minijira.entity.User;
import ru.nstu.upp.minijira.entity.UserState;
import ru.nstu.upp.minijira.exception.CompanyNotFoundException;
import ru.nstu.upp.minijira.exception.UserNotFoundException;
import ru.nstu.upp.minijira.repository.CompanyRepository;
import ru.nstu.upp.minijira.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final MapperFacade mapperFacade;

    public UserService(UserRepository userRepository, CompanyRepository companyRepository, MapperFacade mapperFacade) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.mapperFacade = mapperFacade;
    }

    public List<User> getCompanyUsers(UUID companyId) {
        if(companyRepository.existsById(companyId)) {
            return userRepository.findAllByCompanyId(companyId);
        } else {
            throw new CompanyNotFoundException();
        }
    }

    public UserDto setUserState(UUID userId, UserState state) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            User entity = user.get();
            entity.setState(state);
            return map(userRepository.save(entity));
        }
        throw new UserNotFoundException();
    }

    public UserDto map(User user) {
        return mapperFacade.map(user, UserDto.class);
    }

    public UserDto setAdmin(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            User entity = user.get();
            entity.setAdmin(true);
            return map(userRepository.save(entity));
        }
        throw new UserNotFoundException();
    }

    public UserDto removeAdmin(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            User entity = user.get();
            entity.setAdmin(false);
            return map(userRepository.save(entity));
        }
        throw new UserNotFoundException();
    }
}
