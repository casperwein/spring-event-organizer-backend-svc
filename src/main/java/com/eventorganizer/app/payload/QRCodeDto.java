package com.eventorganizer.app.payload;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class QRCodeDto {
    private long id;
    private long pesertaid;
    private long eventid;
    private String status;
    private String qrcode_path;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
