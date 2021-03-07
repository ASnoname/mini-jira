package ru.nstu.upp.minijira.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nstu.upp.minijira.entity.User;
import ru.nstu.upp.minijira.repository.UserRepository;
import ru.nstu.upp.minijira.security.jwt.JwtUserFactory;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User with login: " + login + " not found");
        }
        return JwtUserFactory.create(user);
    }
}
