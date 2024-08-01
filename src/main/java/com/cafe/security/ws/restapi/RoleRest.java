package com.cafe.security.ws.restapi;


import com.cafe.security.service.facade.RoleService;
import com.cafe.security.ws.converter.RoleConverter;
import com.cafe.security.ws.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/role")
public class RoleRest {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleConverter roleConverter;

    public RoleRest(RoleService roleService, RoleConverter roleConverter) {
        this.roleService = roleService;
        this.roleConverter = roleConverter;
    }

    @GetMapping("/")
    public List<RoleDto> findAll() {
        return roleConverter.toDto(this.roleService.findAll());
    }
}
