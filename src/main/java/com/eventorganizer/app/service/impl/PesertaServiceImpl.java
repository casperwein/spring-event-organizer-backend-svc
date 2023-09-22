package com.eventorganizer.app.service.impl;

import com.eventorganizer.app.entity.Peserta;
import com.eventorganizer.app.payload.PesertaDto;
import com.eventorganizer.app.payload.QRCodeDto;
import com.eventorganizer.app.repository.PesertaRepository;
import com.eventorganizer.app.service.PesertaService;
import com.eventorganizer.app.service.QRCodeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PesertaServiceImpl implements PesertaService {
    private PesertaRepository pesertaRepository;
    private QRCodeService qrCodeService;
    public PesertaServiceImpl(PesertaRepository pesertaRepository, QRCodeService qrCodeService) {
        this.pesertaRepository = pesertaRepository;
        this.qrCodeService = qrCodeService;
    }

    @Override
    public PesertaDto createPeserta(PesertaDto pesertaDto) {
        Peserta peserta = mapToEntity(pesertaDto);
        Peserta newPeserta = pesertaRepository.save(peserta);
        PesertaDto pesertaRes = mapToDTO(newPeserta);

        QRCodeDto qrCodeDto = new QRCodeDto();
        qrCodeDto.setPesertaid(pesertaRes.getIdpeserta());
        qrCodeDto.setEventid(pesertaRes.getEventid());
        qrCodeDto.setStatus("Open");
        qrCodeService.generateQRCode(qrCodeDto);

        return pesertaRes;
    }

    @Override
    public List<PesertaDto> getAllPesertaByEventId(long eventid) {
        List<Peserta> peserta = pesertaRepository.findByeventid(eventid);
        return peserta.stream().map(pst -> mapToDTO(pst)).collect(Collectors.toList());
    }

    @Override
    public List<PesertaDto> getAllPeserta() {
        List<Peserta> pesertas = pesertaRepository.findAll();
        return pesertas.stream().map(ps -> mapToDTO(ps)).collect(Collectors.toList());
    }

    public PesertaDto mapToDTO(Peserta peserta){
        PesertaDto pesertaDto = new PesertaDto();

        pesertaDto.setIdpeserta(peserta.getIdpeserta());
        pesertaDto.setNama(peserta.getNama());
        pesertaDto.setTelepon(peserta.getTelepon());
        pesertaDto.setEmail(peserta.getEmail());
        pesertaDto.setAlamat(peserta.getAlamat());
        pesertaDto.setLos(peserta.getLos());
        pesertaDto.setEventid(peserta.getEventid());
        pesertaDto.setCreatedAt(peserta.getCreatedAt());
        pesertaDto.setUpdatedAt(peserta.getUpdatedAt());

        return pesertaDto;
    }

    public Peserta mapToEntity(PesertaDto pesertaDto){
        Peserta peserta = new Peserta();

        peserta.setNama(pesertaDto.getNama());
        peserta.setTelepon(pesertaDto.getTelepon());
        peserta.setEmail(pesertaDto.getEmail());
        peserta.setAlamat(pesertaDto.getAlamat());
        peserta.setLos(pesertaDto.getLos());
        peserta.setEventid(pesertaDto.getEventid());

        return peserta;
    }
}
