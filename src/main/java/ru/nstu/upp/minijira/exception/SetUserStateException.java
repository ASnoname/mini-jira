package ru.nstu.upp.minijira.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SetUserStateException extends RuntimeException {
    public SetUserStateException() {
        super("Невозможно перевести пользователя в данный статус");
    }
}
