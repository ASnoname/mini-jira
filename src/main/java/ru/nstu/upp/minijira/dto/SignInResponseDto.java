package ru.nstu.upp.minijira.dto;

public class SignInResponseDto {
    private String login;
    private String token;
    private Boolean isAdmin;

    public SignInResponseDto(String login, String token, Boolean isAdmin) {
        this.login = login;
        this.token = token;
        this.isAdmin = isAdmin;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
