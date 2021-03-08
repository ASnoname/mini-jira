package ru.nstu.upp.minijira.service;

import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import ru.nstu.upp.minijira.dto.UserDto;
import ru.nstu.upp.minijira.entity.User;
import ru.nstu.upp.minijira.entity.UserState;
import ru.nstu.upp.minijira.exception.SetUserStateException;
import ru.nstu.upp.minijira.exception.UserNotFoundException;
import ru.nstu.upp.minijira.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MapperFacade mapperFacade;

    public UserService(UserRepository userRepository, MapperFacade mapperFacade) {
        this.userRepository = userRepository;
        this.mapperFacade = mapperFacade;
    }

    public List<UserDto> getCompanyUsers(UUID companyId) {
         return userRepository.findAllByCompanyId(companyId)
                 .stream()
                 .map(this::map)
                 .collect(Collectors.toList());
    }

    public UserDto setUserState(UUID userId, UserState state) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User entity = user.get();
            if(entity.getState().hasAuthority(state)) {
                entity.setState(state);
                return map(userRepository.save(entity));
            } else {
                throw new SetUserStateException();
            }
        }
        throw new UserNotFoundException();
    }

    private UserDto map(User user) {
        return mapperFacade.map(user, UserDto.class);
    }

    public UserDto setAdmin(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User entity = user.get();
            if(entity.getState().equals(UserState.ACTIVE)) {
                entity.setAdmin(true);
                return map(userRepository.save(entity));
            }
        }
        throw new UserNotFoundException();
    }

    public UserDto removeAdmin(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User entity = user.get();
            entity.setAdmin(false);
            return map(userRepository.save(entity));
        }
        throw new UserNotFoundException();
    }

    public void remove(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User entity = user.get();
            userRepository.delete(entity);
        }
        throw new UserNotFoundException();
    }
}
