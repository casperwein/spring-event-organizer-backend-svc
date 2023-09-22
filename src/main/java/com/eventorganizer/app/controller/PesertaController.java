package com.eventorganizer.app.controller;

import com.eventorganizer.app.payload.PesertaDto;
import com.eventorganizer.app.service.PesertaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spring/eo/v1/peserta")
public class PesertaController {
    private PesertaService pesertaService;

    public PesertaController(PesertaService pesertaService) {
        this.pesertaService = pesertaService;
    }

    @PostMapping
    public ResponseEntity<PesertaDto> createPeserta(@RequestBody PesertaDto pesertaDto){
        return new ResponseEntity<>(pesertaService.createPeserta(pesertaDto), HttpStatus.CREATED);
    }

    @GetMapping("/event/{id}")
    public List<PesertaDto> getPesertaByEventId(@PathVariable(value = "id") long eventid){
        return pesertaService.getAllPesertaByEventId(eventid);
    }

    @PreAuthorize("hasRole('SCANNER')") // kebalik
    @GetMapping
    public List<PesertaDto> getAllPeserta(){
        return pesertaService.getAllPeserta();
    }
}
