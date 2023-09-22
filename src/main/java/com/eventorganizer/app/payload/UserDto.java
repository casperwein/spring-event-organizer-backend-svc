package com.eventorganizer.app.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private long id;
    private String nama;
    private String username;
    private String password;
    private String status;
    private String role;
    private String telepon;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
