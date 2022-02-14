package org.aibles.failwall.mail.service;

import org.aibles.failwall.mail.dto.MailRequestDTO;

public interface IMailService {
    void sendMail (MailRequestDTO mailRequestDTO);
}
