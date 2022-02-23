package org.aibles.failwall.user.service;

import org.aibles.failwall.user.dto.request.ActiveUserFormRequestDto;

public interface IGetOTPActiveUserService {

    void execute(ActiveUserFormRequestDto activeUserFormRequestDto);

}
