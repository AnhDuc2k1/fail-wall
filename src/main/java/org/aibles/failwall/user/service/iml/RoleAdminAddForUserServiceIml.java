package org.aibles.failwall.user.service.iml;

import lombok.RequiredArgsConstructor;
import org.aibles.failwall.exception.FailWallBusinessException;
import org.aibles.failwall.user.dto.response.RoleAddForUserResDto;
import org.aibles.failwall.user.model.Role;
import org.aibles.failwall.user.model.User;
import org.aibles.failwall.user.repository.RoleRepository;
import org.aibles.failwall.user.repository.UserRepository;
import org.aibles.failwall.user.repository.UserRoleRepository;
import org.aibles.failwall.user.service.RoleAdminAddForUserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleAdminAddForUserServiceIml implements RoleAdminAddForUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;

    @Override
    public RoleAddForUserResDto execute(long userId) {
        Role role = roleRepository.findById(1L).get();
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.addRoleForUser(role);
            userRepository.save(user);
            RoleAddForUserResDto roleAddForUserResDto = modelMapper.map(user, RoleAddForUserResDto.class);
            roleAddForUserResDto.setRoles(userRoleRepository.getAllByUserId(userId));
            return roleAddForUserResDto;
        }
        else throw new FailWallBusinessException("User Not Found", HttpStatus.NOT_FOUND);
    }
}
