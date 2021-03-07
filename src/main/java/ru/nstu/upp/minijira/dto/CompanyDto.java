package ru.nstu.upp.minijira.dto;

import ru.nstu.upp.minijira.entity.User;

import java.util.List;
import java.util.UUID;

public class CompanyDto {

    private UUID id;
    private String name;
    private String inviteCode;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
