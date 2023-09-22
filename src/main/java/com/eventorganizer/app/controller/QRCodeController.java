package com.eventorganizer.app.controller;

import com.eventorganizer.app.payload.QRCodeDto;
import com.eventorganizer.app.service.QRCodeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spring/eo/v1/qrcode")
public class QRCodeController {
    private QRCodeService qrCodeService;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping
    public List<QRCodeDto> getAllQrCode(){
        return qrCodeService.getAllQRCode();
    }

    @GetMapping("/scan/{id}")
    public String scanQrCode(@PathVariable(value = "id") long id){
        return qrCodeService.scanQRCode(id);
    }
}
