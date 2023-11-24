package com.eventorganizer.app.controller;

import com.eventorganizer.app.payload.CustomeResponse;
import com.eventorganizer.app.payload.PesertaDto;
import com.eventorganizer.app.service.PesertaService;
import com.eventorganizer.app.util.Utils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/spring/eo/v1/peserta")
public class PesertaController {
    private PesertaService pesertaService;
    Utils utils = new Utils();

    public PesertaController(PesertaService pesertaService) {
        this.pesertaService = pesertaService;
    }

    @PostMapping
    public ResponseEntity<CustomeResponse> createPeserta(@RequestBody PesertaDto pesertaDto){
        CustomeResponse customeResponse = pesertaService.createPeserta(pesertaDto);
        customeResponse.setStatusCode(HttpServletResponse.SC_OK);
        return new ResponseEntity<>(customeResponse, HttpStatus.OK);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<CustomeResponse> getPesertaByEventId(@PathVariable(value = "id") long eventid){
        List<PesertaDto> pesertas = pesertaService.getAllPesertaByEventId(eventid);
        CustomeResponse customeResponse = utils.customeResponses();
        customeResponse.setData(pesertas);
        return new ResponseEntity<>(customeResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CustomeResponse> getAllPeserta(){
        List<PesertaDto> pesertas = pesertaService.getAllPeserta();
        CustomeResponse customeResponse = utils.customeResponses();
        customeResponse.setData(pesertas);
        return new ResponseEntity<>(customeResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomeResponse> getPesertaById(@PathVariable(value = "id") long id){
        PesertaDto peserta = pesertaService.getPesertaById(id);
        CustomeResponse customeResponse = utils.customeResponses();
        List<PesertaDto> pesertaDtos = new ArrayList<>();
        pesertaDtos.add(peserta);
        customeResponse.setData(pesertaDtos);

        return new ResponseEntity<>(customeResponse, HttpStatus.OK);
    }
}
