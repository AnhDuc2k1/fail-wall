package org.aibles.failwall.authentication.service;

import org.aibles.failwall.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalService implements UserDetailsService {

    private final IUserRepository userRepository;

    @Autowired
    public UserPrincipalService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> new UserPrincipal(user.getEmail(), user.getPassword()))
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException(email);
                });
    }

}
