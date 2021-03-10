package ru.nstu.upp.minijira.entity;

import java.beans.PropertyEditorSupport;
import java.util.List;

public enum UserState {
    WAITING,
    ACTIVE,
    ARCHIVE;

    static {
        WAITING.nextStates = List.of(ACTIVE);
        ARCHIVE.nextStates = List.of(ACTIVE);
        ACTIVE.nextStates = List.of(ARCHIVE);
    }

    private List<UserState> nextStates;

    public boolean hasAuthority(UserState newState) {
        return nextStates.contains(newState);
    }

}
