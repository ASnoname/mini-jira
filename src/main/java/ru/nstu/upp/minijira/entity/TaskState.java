package ru.nstu.upp.minijira.entity;

import java.util.List;

public enum TaskState {
    CREATED,
    IN_PROGRESS,
    WAITING_APPROVED,
    CLOSED,
    DELETED;

    private List<TaskState> nextStates;

    static {
        CREATED.nextStates = List.of(IN_PROGRESS, DELETED);
        IN_PROGRESS.nextStates = List.of(WAITING_APPROVED, DELETED);
        WAITING_APPROVED.nextStates = List.of(IN_PROGRESS, CLOSED);
        CLOSED.nextStates = List.of();
        DELETED.nextStates = List.of();
    }

    public boolean hasAuthority(TaskState newState, boolean isExecutor, boolean isReporter) {
        if (nextStates.contains(newState)) {
            if (isExecutor && List.of(IN_PROGRESS, WAITING_APPROVED).contains(newState)) {
                return true;
            }
            return isReporter && List.of(IN_PROGRESS, CLOSED, DELETED).contains(newState);
        } else {
            return false;
        }
    }
}
