package com.eventorganizer.app.service.impl;

import com.eventorganizer.app.entity.Events;
import com.eventorganizer.app.entity.Peserta;
import com.eventorganizer.app.exception.ResourceNotFoundException;
import com.eventorganizer.app.payload.CustomeResponse;
import com.eventorganizer.app.payload.PesertaDto;
import com.eventorganizer.app.payload.QRCodeDto;
import com.eventorganizer.app.repository.EventsRepository;
import com.eventorganizer.app.repository.PesertaRepository;
import com.eventorganizer.app.service.PesertaService;
import com.eventorganizer.app.service.QRCodeService;
import com.eventorganizer.app.util.Utils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PesertaServiceImpl implements PesertaService {
    private PesertaRepository pesertaRepository;
    private QRCodeService qrCodeService;
    private EventsRepository eventsRepository;

    public PesertaServiceImpl(PesertaRepository pesertaRepository, EventsRepository eventsRepository, QRCodeService qrCodeService) {
        this.pesertaRepository = pesertaRepository;
        this.qrCodeService = qrCodeService;
        this.eventsRepository = eventsRepository;
    }

    @Override
    public CustomeResponse createPeserta(PesertaDto pesertaDto) {
        long eventId = pesertaDto.getEventid();
        Events event = eventsRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("event", "id", eventId));
        long totalRegistered = pesertaRepository.getTotalPesertaByEventId(eventId);

        Utils utils = new Utils();
        CustomeResponse customeResponse = utils.customeResponses();

        if (totalRegistered < event.getKapasitas()){
            if ("OPEN".equalsIgnoreCase(event.getStatus())   ){
                Peserta peserta = mapToEntity(pesertaDto);
                Peserta newPeserta = pesertaRepository.save(peserta);
                PesertaDto pesertaRes = mapToDTO(newPeserta);

                // hit service generated qr code
                QRCodeDto qrCodeDto = new QRCodeDto();
                qrCodeDto.setPesertaid(newPeserta.getIdpeserta());
                qrCodeDto.setEventid(newPeserta.getEventid());
                qrCodeDto.setStatus("OPEN");
                qrCodeService.generateQRCode(qrCodeDto, newPeserta.getNama());

                // set response to client
                String msg = "Registrasi SUCCESS! Tiket masuk dan File deskripsi telah dikirim ke email Anda. " +
                        "Mohon cek email anda secara Berkala. Terimakasih.";
                customeResponse.setData(pesertaRes);
                customeResponse.setMessage(msg);
            } else {
                customeResponse.setMessage("Registrasi sudah Tutup!");
            }
        } else {
            customeResponse.setMessage("Kuota Sudah Penuh!");
        }
        return customeResponse;
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

    @Override
    public PesertaDto getPesertaById(long id) {
        Peserta peserta = pesertaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Peserta", "peserta", id));
        PesertaDto pesertaDto = mapToDTO(peserta);
        return pesertaDto;
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
