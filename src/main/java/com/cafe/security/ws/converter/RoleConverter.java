package com.cafe.security.ws.converter;

import com.cafe.myutils.webservice.converter.AbstractConverter;
import com.cafe.security.bean.Role;

import com.cafe.security.ws.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter extends AbstractConverter<Role, RoleDto> {
    @Autowired
    @Lazy
    private UserConverter userConverter;

    private boolean user = true;

    @Override
    protected void config() {
        this.dtoType = RoleDto.class;
        this.itemType = Role.class;
    }

    @Override
    protected void convertersConfig(boolean value) {
        this.userConverter.setRole(value);
    }

    @Override
    protected RoleDto convertToDto(Role item) {
        var dto = new RoleDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setUsers(user ? userConverter.toDto(item.getUsers()) : null);
        return dto;
    }

    @Override
    protected Role convertToItem(RoleDto dto) {
        var item = new Role();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setUsers(userConverter.toItem(dto.getUsers()));
        return item;
    }

    public UserConverter getUserConverter() {
        return userConverter;
    }

    public void setUserConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

}
