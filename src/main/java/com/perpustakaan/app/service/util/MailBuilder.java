package com.perpustakaan.app.service.util;

import org.springframework.mail.SimpleMailMessage;

import com.perpustakaan.app.controller.Mail;

public class MailBuilder {
    
    private final SimpleMailMessage message;
    
    public MailBuilder() {
        message = new SimpleMailMessage();
        message.setFrom(Mail.FROM);
    }
    
    public MailBuilder to(String to) {
        message.setTo(to);
        return this;
    }
    public MailBuilder text(String text) {
        message.setText(text);
        return this;
    }
    public MailBuilder subject(String subject) {
        message.setSubject(subject);
        return this;
    }
    
    public SimpleMailMessage build() {
        return message;
    }
    
}
