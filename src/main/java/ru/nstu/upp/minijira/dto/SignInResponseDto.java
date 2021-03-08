package ru.nstu.upp.minijira.dto;

public class SignInResponseDto {
    private String login;
    private String token;
    private Boolean isAdmin;
    private CompanyDto company;

    public SignInResponseDto(String login, String token, Boolean isAdmin, CompanyDto company) {
        this.login = login;
        this.token = token;
        this.isAdmin = isAdmin;
        this.company = company;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
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
