package com.cafe.security.ws.dto;


import com.cafe.myutils.webservice.dto.AuditBaseDto;

import java.util.List;

public class RoleDto extends AuditBaseDto {
    private String name;
    private String label;

    private List<UserDto> users;

    public RoleDto() {
    }

    public String getName() {
        return this.name;
    }

    public String getLabel() {
        return this.label;
    }

    public List<UserDto> getUsers() {
        return this.users;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
