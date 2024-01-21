package com.eventorganizer.app.controller;

import com.eventorganizer.app.payload.CustomeResponse;
import com.eventorganizer.app.payload.QRCodeDto;
import com.eventorganizer.app.service.QRCodeService;
import com.eventorganizer.app.util.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spring/eo/v1/qrcode")
public class QRCodeController {
    private QRCodeService qrCodeService;
    Utils utils = new Utils();

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping
    public ResponseEntity<CustomeResponse> getAllQrCode(){
        CustomeResponse customeResponse = utils.customeResponses();
        List<QRCodeDto> qrCode = qrCodeService.getAllQRCode();
        customeResponse.setData(qrCode);
        return new ResponseEntity<>(customeResponse, HttpStatus.OK);
    }

    @GetMapping("/scan/{id}")
    public ResponseEntity<CustomeResponse> scanQrCode(@PathVariable(value = "id") long id){
        CustomeResponse customeResponse = qrCodeService.scanQRCode(id);
        return new ResponseEntity<>(customeResponse, HttpStatus.valueOf(customeResponse.getStatusCode()));
    }
}
