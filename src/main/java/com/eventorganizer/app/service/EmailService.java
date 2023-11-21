package com.eventorganizer.app.service;

public interface EmailService {
//    void sentEmailQRCode(String to, String subject, String body);
    void sentEmailQRCode(String to, String subject, String body, String attachmentPath, String eventDocPath, String eventName);
}
