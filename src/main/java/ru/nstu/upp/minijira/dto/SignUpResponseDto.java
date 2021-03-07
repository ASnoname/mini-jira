package ru.nstu.upp.minijira.dto;

public class SignUpResponseDto {
    private String login;

    public SignUpResponseDto(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
