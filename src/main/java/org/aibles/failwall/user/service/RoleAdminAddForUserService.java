package org.aibles.failwall.user.service;

import org.aibles.failwall.user.dto.response.RoleAddForUserResDto;

import java.util.List;

public interface RoleAdminAddForUserService {
    RoleAddForUserResDto execute(long userId);
}
