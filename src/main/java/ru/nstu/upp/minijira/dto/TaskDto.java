package ru.nstu.upp.minijira.dto;

import java.util.Date;

public class TaskDto {
    private String title;
    private String description;
    private UserDto executor;
    private UserDto reporter;
    private String state;
    private Date deadline;
    private Date createDate;
    private Boolean isReporter;
    private Boolean isExecutor;

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

    public UserDto getExecutor() {
        return executor;
    }

    public void setExecutor(UserDto executor) {
        this.executor = executor;
    }

    public Boolean getIsExecutor() {
        return isExecutor;
    }

    public void setIsExecutor(Boolean executor) {
        isExecutor = executor;
    }

    public UserDto getReporter() {
        return reporter;
    }

    public void setReporter(UserDto reporter) {
        this.reporter = reporter;
    }

    public Boolean getIsReporter() {
        return isReporter;
    }

    public void setIsReporter(Boolean reporter) {
        isReporter = reporter;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public static class UserDto {
        private String name;
        private String lastName;

        public UserDto(String name, String lastName) {
            this.name = name;
            this.lastName = lastName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
