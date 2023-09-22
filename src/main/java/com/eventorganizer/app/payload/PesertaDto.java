package com.eventorganizer.app.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PesertaDto {
    private long idpeserta;
    private String nama;
    private String email;
    private String telepon;
    private String alamat;
    private String los;
    private long eventid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
