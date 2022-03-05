package org.aibles.failwall.mail.service.impl;

import org.aibles.failwall.mail.dto.MailRequestDTO;
import org.aibles.failwall.mail.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class IMailServiceImpl implements IMailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    public IMailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async("asyncExecutor")
    public void sendMail(MailRequestDTO mailRequestDTO) {
        javaMailSender.send(createMailMessage(mailRequestDTO));
    }

    private SimpleMailMessage createMailMessage(MailRequestDTO mailRequestDTO){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailFrom);
        mailMessage.setTo(mailRequestDTO.getReceiver());
        mailMessage.setSubject(mailRequestDTO.getSubject());
        mailMessage.setText(mailRequestDTO.getMessage());
        return mailMessage;
    }

}
