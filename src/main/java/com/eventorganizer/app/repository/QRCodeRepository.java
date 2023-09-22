package com.eventorganizer.app.repository;

import com.eventorganizer.app.entity.QRCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QRCodeRepository extends JpaRepository<QRCodeEntity, Long> {
    QRCodeEntity findBarcodeById(long id);
}
