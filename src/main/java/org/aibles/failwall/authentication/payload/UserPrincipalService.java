package org.aibles.failwall.authentication.payload;

import org.aibles.failwall.user.model.Role;
import org.aibles.failwall.user.repository.UserRepository;
import org.aibles.failwall.user.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserPrincipalService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserPrincipalService(UserRepository userRepository, UserRoleRepository userRoleRepository){
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> {
                    Set<Role> roles = userRoleRepository.getAllByUserId(user.getId());
                    return new UserPrincipal(user.getEmail(), user.getPassword(), roles);
                })
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException(email);
                });
    }

}
