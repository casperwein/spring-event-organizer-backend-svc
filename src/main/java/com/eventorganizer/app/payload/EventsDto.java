package com.eventorganizer.app.payload;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class EventsDto {
    private long id;
    private String nama;
    private String tempat;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private int lengthofevent;
    private long kapasitas;
    private String keterangan;
    private String eventpathfiledesc;
    private String status;
    private String linkregistration;
    MultipartFile file;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
