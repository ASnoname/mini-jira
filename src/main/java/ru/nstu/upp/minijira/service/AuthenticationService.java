package ru.nstu.upp.minijira.service;

import ma.glasnost.orika.MapperFacade;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nstu.upp.minijira.dto.*;
import ru.nstu.upp.minijira.entity.Company;
import ru.nstu.upp.minijira.entity.User;
import ru.nstu.upp.minijira.entity.UserState;
import ru.nstu.upp.minijira.exception.CompanyNotFoundException;
import ru.nstu.upp.minijira.exception.UserExistException;
import ru.nstu.upp.minijira.repository.CompanyRepository;
import ru.nstu.upp.minijira.repository.UserRepository;
import ru.nstu.upp.minijira.security.jwt.JwtTokenProvider;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MapperFacade mapperFacade;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtTokenProvider jwtTokenProvider,
                                 UserRepository userRepository,
                                 CompanyRepository companyRepository,
                                 BCryptPasswordEncoder passwordEncoder,
                                 MapperFacade mapperFacade) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapperFacade = mapperFacade;
    }

    public SignInResponseDto signIn(SignInRequestDto requestDto) {
        try {
            String login = requestDto.getLogin();
            User user = userRepository.findByLogin(login);
            if (user == null || !UserState.ACTIVE.equals(user.getState())) {
                throw new UsernameNotFoundException("Пользователь с логином " + login + " не найден");
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, requestDto.getPassword()));

            String token = jwtTokenProvider.createToken(login, user.getAdmin());
            return new SignInResponseDto(login, token, user.getAdmin());
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Логин/пароль неверные");
        }
    }

    public SignUpResponseDto signUp(SignUpRequestDto requestDto, String inviteCode) {
        Company company = companyRepository.getByInviteCode(inviteCode);
        if(company == null) {
            throw new CompanyNotFoundException();
        }
        String login = requestDto.getLogin();
        boolean isExist = userRepository.existsByLogin(login);
        if (isExist) {
            throw new UserExistException(login);
        }

        User user = map(requestDto);
        user.setPasswordHash(passwordEncoder.encode(requestDto.getPassword()));

        if (company.getUsers().isEmpty()) {
            user.setAdmin(true);
            user.setState(UserState.ACTIVE);
        } else {
            user.setAdmin(false);
            user.setState(UserState.WAITING);
        }

        user.setCompany(company);
        userRepository.save(user);

        return new SignUpResponseDto(login);
    }

    private User map(SignUpRequestDto requestDto) {
        return mapperFacade.map(requestDto, User.class);
    }
}
