package org.aibles.failwall.user.service;

import org.aibles.failwall.user.dto.request.ActiveUserFormRequestDto;

public interface GetOTPActiveUserService {

    void execute(ActiveUserFormRequestDto activeUserFormRequestDto);

}
