package ru.nstu.upp.minijira.dto;

import java.util.List;

public class TaskViewResponse {
    private List<TaskDto> tasks;

    public TaskViewResponse(List<TaskDto> tasks) {
        this.tasks = tasks;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }
}
