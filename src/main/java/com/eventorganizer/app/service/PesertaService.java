package com.eventorganizer.app.service;

import com.eventorganizer.app.payload.CustomeResponse;
import com.eventorganizer.app.payload.PesertaDto;

import java.util.List;

public interface PesertaService {
    CustomeResponse createPeserta(PesertaDto pesertaDto);
    List<PesertaDto> getAllPesertaByEventId(long eventid);
    List<PesertaDto> getAllPeserta();
    PesertaDto getPesertaById(long id);
}
