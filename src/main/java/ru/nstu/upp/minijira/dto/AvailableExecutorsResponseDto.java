package ru.nstu.upp.minijira.dto;

import java.util.List;

public class AvailableExecutorsResponseDto {
    private List<Executor> executors;

    public AvailableExecutorsResponseDto(List<Executor> executors) {
        this.executors = executors;
    }

    public List<Executor> getExecutors() {
        return executors;
    }

    public void setExecutors(List<Executor> executors) {
        this.executors = executors;
    }

    public static class Executor {
        String executorLogin;
        String name;
        String lastName;

        public Executor(String executorLogin, String name, String lastName) {
            this.executorLogin = executorLogin;
            this.name = name;
            this.lastName = lastName;
        }

        public String getExecutorLogin() {
            return executorLogin;
        }

        public void setExecutorLogin(String executorLogin) {
            this.executorLogin = executorLogin;
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
