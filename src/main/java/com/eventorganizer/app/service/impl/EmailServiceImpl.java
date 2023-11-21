package com.eventorganizer.app.service.impl;

import com.eventorganizer.app.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sentEmailQRCode(String to, String subject, String body,
                                String attachment, String eventDocPath, String eventName) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("casperwein@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            Path path = Paths.get("public/qrcode-img", attachment);
            Path eventPathDoc = Paths.get("public/event", eventDocPath);
            Resource attachQr = new UrlResource(path.toUri());
            Resource attachEventDoc = new UrlResource(eventPathDoc.toUri());
            helper.addAttachment(attachment, attachQr.getFile());
            helper.addAttachment(eventName, attachEventDoc.getFile());

            javaMailSender.send(message);
            System.out.println("sent email successfully");

        } catch (MessagingException | IOException e ){
            e.printStackTrace();
        }
    }
}
