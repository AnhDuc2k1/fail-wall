package org.aibles.failwall.user.service.iml;

import com.google.common.cache.LoadingCache;
import org.aibles.failwall.exception.BadRequestException;
import org.aibles.failwall.exception.OtpExpiredException;
import org.aibles.failwall.exception.WrongOtpCodeException;
import org.aibles.failwall.user.dto.request.ActiveUserFormRequestDto;
import org.aibles.failwall.user.models.User;
import org.aibles.failwall.user.repository.IUserRepository;
import org.aibles.failwall.user.service.IActiveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Service
public class ActiveUserServiceIml implements IActiveUserService {

    private final IUserRepository userRepository;
    private final LoadingCache<String, String> otpCache;

    @Autowired
    public ActiveUserServiceIml(IUserRepository userRepository, LoadingCache<String, String> otpCache) {
        this.userRepository = userRepository;
        this.otpCache = otpCache;
    }

    @Override
    public void execute(ActiveUserFormRequestDto activeUserFormRequestDto) {
        String email = activeUserFormRequestDto.getEmail();
        validateInput(activeUserFormRequestDto);
        User user = userRepository.findByEmail(email).get();
        user.setIsActived(true);
        userRepository.save(user);
    }

    private void validateInput(ActiveUserFormRequestDto activeUserFormRequestDto) {
        HashMap<String, String> error = new HashMap<>();
        userRepository.findByEmail(activeUserFormRequestDto.getEmail()).ifPresentOrElse(
                user -> {
                    if (userRepository.isActiveUser(user.getEmail()).get()) {
                        error.put("user", " is activated");
                    }
                },
                () -> error.put("user", "was not registered")
        );
        if (!error.isEmpty()) {
            throw new BadRequestException(error);
        }
        String email = activeUserFormRequestDto.getEmail();
        String otpCode = activeUserFormRequestDto.getOtp();
        try {
            if (!otpCache.get(email).equals(otpCode)) {
                throw new WrongOtpCodeException();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new OtpExpiredException();
        }
    }
}
