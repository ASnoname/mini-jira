package ru.nstu.upp.minijira.service;

import ma.glasnost.orika.MapperFactory;
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
    private final MapperFactory mapperFactory;

    public UserService(UserRepository userRepository, MapperFactory mapperFactory) {
        this.userRepository = userRepository;
        this.mapperFactory = mapperFactory;
    }

    public List<UserDto> getCompanyUsers(UUID companyId, UserState userState) {
        return userState != null ? userRepository.findAllByCompanyIdAndState(companyId, userState)
                .stream()
                .map(this::map)
                .collect(Collectors.toList()) : userRepository.findAllByCompanyId(companyId)
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

    public UserDto setAdmin(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User entity = user.get();
            if(entity.getState().equals(UserState.ACTIVE)) {
                entity.setIsAdmin(true);
                return map(userRepository.save(entity));
            }
        }
        throw new UserNotFoundException();
    }

    public UserDto removeAdmin(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User entity = user.get();
            entity.setIsAdmin(false);
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

    public UserDto getById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return map(user.get());
        } else {
            throw new UserNotFoundException();
        }
    }

    private UserDto map(User entity) {
        return mapperFactory.getMapperFacade().map(entity, UserDto.class);
    }
}
