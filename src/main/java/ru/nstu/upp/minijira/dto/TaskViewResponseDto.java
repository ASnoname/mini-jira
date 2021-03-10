package ru.nstu.upp.minijira.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskViewResponseDto {
    private List<TaskInfo> reporter;
    private List<TaskInfo> executor;

    public TaskViewResponseDto(List<TaskInfo> reporter, List<TaskInfo> executor) {
        this.reporter = reporter;
        this.executor = executor;
    }

    public List<TaskInfo> getReporter() {
        return reporter;
    }

    public void setReporter(List<TaskInfo> reporter) {
        this.reporter = reporter;
    }

    public List<TaskInfo> getExecutor() {
        return executor;
    }

    public void setExecutor(List<TaskInfo> executor) {
        this.executor = executor;
    }

    public static class TaskInfo {
        private UUID id;
        private String title;
        private Date createDate;
        private String state;

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Date getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
