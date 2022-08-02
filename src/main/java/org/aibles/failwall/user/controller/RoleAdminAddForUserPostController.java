package org.aibles.failwall.user.controller;

import org.aibles.failwall.user.dto.response.RoleAddForUserResDto;
import org.aibles.failwall.user.service.RoleAdminAddForUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admins")
public class RoleAdminAddForUserPostController {

    private final RoleAdminAddForUserService roleAdminAddForUserService;

    @Autowired
    public RoleAdminAddForUserPostController(RoleAdminAddForUserService roleAdminAddForUserService) {
        this.roleAdminAddForUserService = roleAdminAddForUserService;
    }

    @PostMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleAddForUserResDto> execute(@PathVariable long userId){
        return new ResponseEntity<>(roleAdminAddForUserService.execute(userId), HttpStatus.OK);
    }

}
