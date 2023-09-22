package com.eventorganizer.app.service.impl;

import com.eventorganizer.app.entity.Peserta;
import com.eventorganizer.app.entity.QRCodeEntity;
import com.eventorganizer.app.payload.QRCodeDto;
import com.eventorganizer.app.repository.PesertaRepository;
import com.eventorganizer.app.repository.QRCodeRepository;
import com.eventorganizer.app.service.EmailService;
import com.eventorganizer.app.service.QRCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QRCodeServiceImpl implements QRCodeService {
    private QRCodeRepository qrCodeRepository;
    private PesertaRepository pesertaRepository;
    private EmailService emailService;

    public QRCodeServiceImpl(QRCodeRepository qrCodeRepository, EmailService emailService, PesertaRepository pesertaRepository) {
        this.qrCodeRepository = qrCodeRepository;
        this.emailService = emailService;
        this.pesertaRepository  = pesertaRepository;
    }

    @Override
    public QRCodeDto addQRCode(QRCodeDto qrCodeDto) {
        QRCodeEntity qrCodeEntity = new QRCodeEntity();

        qrCodeEntity.setEventid(qrCodeDto.getEventid());
        qrCodeEntity.setPesertaid(qrCodeDto.getPesertaid());
        qrCodeEntity.setStatus(qrCodeDto.getStatus());
        qrCodeEntity.setQrcode_path(qrCodeDto.getQrcode_path());

        qrCodeRepository.save(qrCodeEntity);

        return null;
    }

    @Override
    public QRCodeDto generateQRCode(QRCodeDto qrCodeDto) {
        String QRMsg = "peserta: " + qrCodeDto.getPesertaid() + ", event: " + qrCodeDto.getEventid();

        try {
            int width = 1000;
            int height = 1000;

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(QRMsg, BarcodeFormat.QR_CODE, width, height);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            String path = "src\\main\\resources\\static\\qrcode\\";
            UUID fileName =  generateUUID();
            String filePath = path + fileName + ".png";
            File file = new File(filePath);
            ImageIO.write(bufferedImage, "png", file);

            // add data qrcode
            qrCodeDto.setQrcode_path(filePath);
            addQRCode(qrCodeDto);

            // sent email
            Peserta peserta = pesertaRepository.findPesertaByidpeserta(qrCodeDto.getPesertaid());
            String email = peserta.getEmail();
            String subject = "Registration Ticket";
            String body = "Ini adalah QR Code untuk tiket masuk Anda!\n" +
                    "Tunjukan QR Code ini kepada petugas untuk discan!";

            emailService.sentEmailQRCode(email, subject, body);
        } catch (IOException | WriterException  e){
            e.printStackTrace();
            System.out.println("Error generated QRCODE");
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<QRCodeDto> getAllQRCode() {
        List<QRCodeEntity> qrcodes = qrCodeRepository.findAll();
        return qrcodes.stream().map(qrCodeEntity -> mapToDTO(qrCodeEntity)).collect(Collectors.toList());
    }

    @Override
    public String scanQRCode(long id) {
        QRCodeEntity qrCodeEntity = qrCodeRepository.findBarcodeById(id);
        qrCodeEntity.setStatus("Close");
        qrCodeRepository.save(qrCodeEntity);
        return "Update Successfully";
    }

    public UUID generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid;
    }

    public QRCodeDto mapToDTO(QRCodeEntity qrCodeEntity){
        QRCodeDto qrCodeDto = new QRCodeDto();

        qrCodeDto.setId(qrCodeEntity.getId());
        qrCodeDto.setEventid(qrCodeEntity.getEventid());
        qrCodeDto.setPesertaid(qrCodeEntity.getPesertaid());
        qrCodeDto.setStatus(qrCodeEntity.getStatus());
        qrCodeDto.setQrcode_path(qrCodeEntity.getQrcode_path());
        qrCodeDto.setCreatedAt(qrCodeEntity.getCreatedAt());
        qrCodeDto.setUpdatedAt(qrCodeEntity.getUpdatedAt());

        return qrCodeDto;
    }
}
