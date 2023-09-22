package com.eventorganizer.app.service.impl;

import com.eventorganizer.app.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
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
    public void sentEmailQRCode(String to, String subject, String body) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("casperwein@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

//            File file = new File(attachmentPath);
//            Path path = Paths.get(attachmentPath);
//            helper.addAttachment(file.getName(), new InputStreamSource() {
//                @Override
//                public InputStream getInputStream() throws IOException {
//                    return Files.newInputStream(path);
//                }
//            });
//            FileSystemResource fileSystemResource = new FileSystemResource(new File(attachmentPath));
//            helper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

            javaMailSender.send(message);
            System.out.println("sent email successfully");

        } catch (MessagingException e){
            e.printStackTrace();
        }
    }
}
