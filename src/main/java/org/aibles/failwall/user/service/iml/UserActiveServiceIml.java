package org.aibles.failwall.user.service.iml;

import com.google.common.cache.LoadingCache;
import org.aibles.failwall.exception.BadRequestException;
import org.aibles.failwall.user.dto.request.ActiveUserFormRequestDto;
import org.aibles.failwall.user.repository.UserRepository;
import org.aibles.failwall.user.service.ActiveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Service
public class UserActiveServiceIml implements ActiveUserService {

    private final UserRepository userRepository;
    private final LoadingCache<String, String> otpCache;

    @Autowired
    public UserActiveServiceIml(UserRepository userRepository, LoadingCache<String, String> otpCache) {
        this.userRepository = userRepository;
        this.otpCache = otpCache;
    }

    @Override
    public void execute(ActiveUserFormRequestDto activeUserFormRequestDto) {
        String email = activeUserFormRequestDto.getEmail();
        validateInput(activeUserFormRequestDto);
        userRepository.findByEmail(email).map( user -> {
            user.setIsActivated(true);
            return userRepository.save(user);
        });
    }

    private void validateInput(ActiveUserFormRequestDto activeUserFormRequestDto) {
        HashMap<String, String> error = new HashMap<>();
        String email = activeUserFormRequestDto.getEmail();
        String otpCode = activeUserFormRequestDto.getOtp();

        userRepository.findByEmail(activeUserFormRequestDto.getEmail()).ifPresentOrElse(
                user -> {
                    if (userRepository.isActiveUserByEmail(user.getEmail())) {
                        error.put("user", " is activated");
                    }
                },
                () -> error.put("user", "was not registered")
        );
        try {
            if (!otpCache.get(email).equals(otpCode)) {
                error.put("otp", " incorrect");
            }
        } catch (ExecutionException e) {
                error.put("otp", " expired");
        }
        if (!error.isEmpty()) {
            throw new BadRequestException(error);
        }
    }
}
