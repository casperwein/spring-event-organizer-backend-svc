package com.eventorganizer.app.repository;

import com.eventorganizer.app.entity.Peserta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PesertaRepository extends JpaRepository<Peserta, Long> {
    List<Peserta> findByeventid(long eventid);
    Peserta findPesertaByidpeserta(long id);
}
