package com.eventorganizer.app.service.impl;

import com.eventorganizer.app.entity.Events;
import com.eventorganizer.app.entity.Peserta;
import com.eventorganizer.app.entity.QRCodeEntity;
import com.eventorganizer.app.payload.CustomeResponse;
import com.eventorganizer.app.payload.QRCodeDto;
import com.eventorganizer.app.repository.PesertaRepository;
import com.eventorganizer.app.repository.QRCodeRepository;
import com.eventorganizer.app.service.EmailService;
import com.eventorganizer.app.service.EventsService;
import com.eventorganizer.app.service.QRCodeService;
import com.eventorganizer.app.util.Utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.lang.String;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QRCodeServiceImpl implements QRCodeService {
    private QRCodeRepository qrCodeRepository;
    private PesertaRepository pesertaRepository;
    private EventsService eventsService;
    private EmailService emailService;
    private final Path path;
    private String pathDir = "public\\qrcode-img\\";


    public QRCodeServiceImpl(QRCodeRepository qrCodeRepository, EventsService eventsService, EmailService emailService, PesertaRepository pesertaRepository) {
        this.qrCodeRepository = qrCodeRepository;
        this.emailService = emailService;
        this.pesertaRepository  = pesertaRepository;
        this.eventsService = eventsService;

        this.path = Path.of(pathDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.path);
        } catch (IOException e){
            throw  new RuntimeException("Gagal membuat folder QRCode!", e);
        }
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
    public QRCodeDto generateQRCode(QRCodeDto qrCodeDto, String pesertaName) {
        String QRMsg = "peserta: " + qrCodeDto.getPesertaid() + ", event: " + qrCodeDto.getEventid();
        Events events = eventsService.getEventDocPath(qrCodeDto.getEventid());
        Peserta peserta = pesertaRepository.findPesertaByidpeserta(qrCodeDto.getPesertaid());
        Utils utils = new Utils();

        try {
            int width = 1000;
            int height = 1000;

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(QRMsg, BarcodeFormat.QR_CODE, width, height);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            String path = "public\\qrcode-img\\";
            UUID fileName =  utils.generateUUID();
            String attachment = fileName + ".png";
            String filePath = path + fileName + ".png";
            File file = new File(filePath);
            ImageIO.write(bufferedImage, "png", file);

            // add data qrcode
            qrCodeDto.setQrcode_path(filePath);
            addQRCode(qrCodeDto);

            // sent email
            String email = peserta.getEmail();
            String subject = "Tiket " + events.getNama();
            String body = "Dear, " + pesertaName+ "\n\nIni adalah TIKET masuk Anda! Harap disimpan dengan baik dan jangan lupa dibawa saat anda datang ke tempat acara.\n Tunjukan tiket ini kepada petugas untuk discan!\n\n" +
                    "Terimakasih\n\n" +
                    "Best Regards,\n" +
                    "Event Organizer OFFICIAL";

            Path eventPath = Paths.get(events.getEventpathfiledesc());
            String fileEventName = eventPath.getFileName().toString();

            emailService.sentEmailQRCode(email, subject, body, attachment, fileEventName, events.getNama());
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
    public CustomeResponse scanQRCode(long id) {
        QRCodeEntity qrCodeEntity = qrCodeRepository.findBarcodeById(id);
        CustomeResponse customeResponse = new CustomeResponse<>();
        customeResponse.setStatusCode(200);

        if(qrCodeEntity != null){
            String currentStatus = qrCodeEntity.getStatus();
            if("OPEN".equalsIgnoreCase(currentStatus)){
                qrCodeEntity.setStatus("CLOSED");
                qrCodeRepository.save(qrCodeEntity);
                customeResponse.setMessage("Scan QR Code Berhasil!");
            } else {
                customeResponse.setMessage("QR Code telah digunakan!!");
            }
        } else {
            customeResponse.setMessage("Data Tidak Ditemukan!");
            customeResponse.setStatusCode(404);
        }
        return customeResponse;
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
