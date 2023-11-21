package com.eventorganizer.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "peserta")
public class Peserta {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long idpeserta;
    private String nama;
    private String email;
    private String telepon;
    private String alamat;
    private String los;
    @Column(name = "eventid")
    private long eventid;
//    @OneToMany(mappedBy = "pesertaid", cascade = CascadeType.ALL)
//    private Set<QRCodeEntity> peserta = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
