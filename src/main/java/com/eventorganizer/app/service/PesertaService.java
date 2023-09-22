package com.eventorganizer.app.service;

import com.eventorganizer.app.payload.PesertaDto;

import java.util.List;

public interface PesertaService {
    PesertaDto createPeserta(PesertaDto pesertaDto);
    List<PesertaDto> getAllPesertaByEventId(long eventid);
    List<PesertaDto> getAllPeserta();
}
