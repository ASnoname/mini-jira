package ru.nstu.upp.minijira.dto;

import java.util.Date;

public class CreateTaskRequestDto {
    private String title;
    private String description;
    private String executorLogin;
    private Date deadline;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExecutorLogin() {
        return executorLogin;
    }

    public void setExecutorLogin(String executorLogin) {
        this.executorLogin = executorLogin;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
