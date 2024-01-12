package com.perpustakaan.app.mail;

import java.io.InputStream;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Profile("test|(!dev&!prod)") @Configuration @Slf4j
@Component("JavaMailSenderImpl")
public class TestJavaMailSenderImpl implements JavaMailSender {

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        log.info("Email: from {} to {} subject {}",simpleMessage.getFrom(),simpleMessage.getTo(),
                simpleMessage.getSubject());
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        for(SimpleMailMessage simpleMessage : simpleMessages) {
            log.info("Email: from {} to {} subject {}",simpleMessage.getFrom(),simpleMessage.getTo(),
                    simpleMessage.getSubject());
        }
    }

    @Override
    public MimeMessage createMimeMessage() {
        return null;
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
        return null;
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
    }

}
