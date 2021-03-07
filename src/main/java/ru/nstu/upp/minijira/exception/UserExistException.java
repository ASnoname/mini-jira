package ru.nstu.upp.minijira.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class UserExistException extends RuntimeException {
    public UserExistException(String login) {
        super("Пользователь с логином " + login + " существует, введите другой");
    }
}
