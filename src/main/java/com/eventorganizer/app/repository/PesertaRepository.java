package com.eventorganizer.app.repository;

import com.eventorganizer.app.entity.Peserta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PesertaRepository extends JpaRepository<Peserta, Long> {
    List<Peserta> findByeventid(long eventid);
    Peserta findPesertaByidpeserta(long id);
    @Query("SELECT COUNT(eventid) FROM Peserta WHERE eventid = :eventId")
    long getTotalPesertaByEventId(@Param("eventId") long eventId);
}
