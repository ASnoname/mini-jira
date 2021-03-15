package ru.nstu.upp.minijira.dto;

import java.util.Date;
import java.util.UUID;

public class UpdateTaskDto {
    private String title;
    private String description;
    private UUID executorId;
    private String state;
    private Date deadline;

    public UpdateTaskDto(String title, String description, UUID executorId, String state, Date deadline) {
        this.title = title;
        this.description = description;
        this.executorId = executorId;
        this.state = state;
        this.deadline = deadline;
    }

    public UpdateTaskDto() {
    }

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

    public UUID getExecutorId() {
        return executorId;
    }

    public void setExecutorId(UUID executorId) {
        this.executorId = executorId;
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
}
