package com.eventorganizer.app.payload;

import com.eventorganizer.app.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private long id;
    private String nama;
    private String email;
    private String username;
    private String password;
    private String status;
    private Role role;
    private String telepon;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
