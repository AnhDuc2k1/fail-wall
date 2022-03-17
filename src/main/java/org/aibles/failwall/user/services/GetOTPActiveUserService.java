package org.aibles.failwall.user.services;

import org.aibles.failwall.user.dto.request.ActiveUserFormRequestDto;

public interface GetOTPActiveUserService {

    void execute(ActiveUserFormRequestDto activeUserFormRequestDto);

}
