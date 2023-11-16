package com.eventorganizer.app.repository;

import com.eventorganizer.app.entity.QRCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QRCodeRepository extends JpaRepository<QRCodeEntity, Long> {
    QRCodeEntity findBarcodeById(long id);
    @Query("SELECT COUNT(pesertaid) FROM QRCodeEntity WHERE eventid = :eventId AND status = 'CLOSE'")
    long getQRTotalScanned(@Param("eventId") long eventId);
    @Query("SELECT COUNT(pesertaid) FROM QRCodeEntity WHERE eventid = :eventId AND status = 'OPEN'")
    long getQRTotalNotScanned(@Param("eventId") long eventId);
}
