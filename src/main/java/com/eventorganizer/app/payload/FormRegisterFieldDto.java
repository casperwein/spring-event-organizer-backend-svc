package com.eventorganizer.app.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FormRegisterFieldDto {
    private long id;
    private long eventid;
    private String nama;
    private String email;
    private String telepon;
    private String alamat;
    private String los;
    private String nik;
    private String mediasosial;
    private String no_kk;
    private String agama;
    private String kec;
    private String kota;
    private String provinsi;
    private String kelurahan;
    private String tanggal_lahir;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
