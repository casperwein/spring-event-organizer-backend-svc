package com.eventorganizer.app.service;

import com.eventorganizer.app.entity.QRCodeEntity;
import com.eventorganizer.app.payload.QRCodeDto;

import java.util.List;

public interface QRCodeService {
    QRCodeDto addQRCode(QRCodeDto qrCodeDto);
    QRCodeDto generateQRCode(QRCodeDto qrCodeDto, String name);
    List<QRCodeDto> getAllQRCode();

    String scanQRCode(long id);
}
