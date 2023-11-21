package com.eventorganizer.app.service.impl;

import com.eventorganizer.app.entity.Events;
import com.eventorganizer.app.exception.ResourceNotFoundException;
import com.eventorganizer.app.payload.CustomeResponse;
import com.eventorganizer.app.payload.EventsDto;
import com.eventorganizer.app.repository.EventsRepository;
import com.eventorganizer.app.repository.PesertaRepository;
import com.eventorganizer.app.repository.QRCodeRepository;
import com.eventorganizer.app.service.EventsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventsServiceImpl implements EventsService {
    private EventsRepository eventsRepository;
    private PesertaRepository pesertaRepository;
    private  QRCodeRepository qrCodeRepository;
    private FileService fileService;

    public EventsServiceImpl(EventsRepository eventsRepository, QRCodeRepository qrCodeRepository, PesertaRepository pesertaRepository, FileService fileService) {
        this.eventsRepository = eventsRepository;
        this.pesertaRepository = pesertaRepository;
        this.qrCodeRepository = qrCodeRepository;
        this.fileService = fileService;
    }

    @Override
    public EventsDto createEvents(EventsDto eventsDto) {
        String pathFile = fileService.saveFile(eventsDto.getFile());
        eventsDto.setEventpathfiledesc(pathFile);
        Events events = mapToEntity(eventsDto);
        events.setStatus("OPEN");
        Events saveEvents = eventsRepository.save(events);
        EventsDto response = mapToDto(saveEvents);
        return response;
    }

    @Override
    public EventsDto updateEvent(EventsDto eventsDto, long id) {
        Events events = eventsRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Event", "id", id));

        events.setNama(eventsDto.getNama());
        events.setTempat(eventsDto.getTempat());
        events.setStart_date(eventsDto.getStart_date());
        events.setEnd_date(eventsDto.getEnd_date());
        events.setLengthofevent(eventsDto.getLengthofevent());
        events.setKapasitas(eventsDto.getKapasitas());
        events.setKeterangan(eventsDto.getKeterangan());
        events.setEventpathfiledesc(eventsDto.getEventpathfiledesc());
        events.setStatus(eventsDto.getStatus());
        events.setLinkregistration(eventsDto.getLinkregistration());

        eventsRepository.save(events);

        EventsDto eventRes = mapToDto(events);

        return eventRes;
    }

    @Override
    public List<EventsDto> getAllEvents() {
        List<Events> events = eventsRepository.findAll();

        return events.stream().map(event -> mapToDto(event)).collect(Collectors.toList());
    }

    @Override
    public EventsDto getEventsById(long id) {
        Events event = eventsRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Events", "id", id));
        EventsDto eventsDto = mapToDto(event);

        return eventsDto;
    }

    @Override
    public String deleteEvent(long id) {
        Events event = eventsRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Events", "id", id));
        eventsRepository.delete(event);
        return "Event Deleted Successfully!";
    }

    @Override
    public CustomeResponse getEventDetails(long eventId) {
        Events event = eventsRepository.findById(eventId).orElseThrow(
                () ->  new ResourceNotFoundException("Events", "id", eventId));
        long kapasitas = event.getKapasitas();
        long totalPeserta = pesertaRepository.getTotalPesertaByEventId(eventId);
        long totalQRScanned = qrCodeRepository.getQRTotalScanned(eventId);
        long totalQRNotScanned = qrCodeRepository.getQRTotalNotScanned(eventId);

        CustomeResponse customeResponse = new CustomeResponse();
        Map<String, Object> data = new HashMap<>();
        data.put("totalPeserta", totalPeserta);
        data.put("qrScanned", totalQRScanned);
        data.put("qrNotYet", totalQRNotScanned);
        data.put("kapasitas", kapasitas);
        data.put("event", event);

        customeResponse.setData(data);
        return customeResponse;
    }

    @Override
    public CustomeResponse getEventsForDashboard() {
        List<Events> events = eventsRepository.findAll();
        CustomeResponse customeResponse = new CustomeResponse();
        Map<String, Object> data = new HashMap<>();

        LocalDate now = LocalDate.now();
        Month thisMonth = now.getMonth();

        long totalEvent = eventsRepository.count();
        long totalPeserta = pesertaRepository.count();
        long totalEventDone = eventsRepository.getTotalEventDone();
        long totalEventIncoming = eventsRepository.getTotalEventIncoming();
        long totalPesertaScanned = qrCodeRepository.getTotalScanned();
        long totalPesertaNotScanned = qrCodeRepository.getTotalNotScanned();
        List<Events> eventsThisMonts = events.stream().filter(
                event -> event.getStart_date().getMonth() == thisMonth).collect(Collectors.toList());
        long totalEventThisMonth = eventsThisMonts.size();
        long totalKapasitas = events.stream().mapToLong(Events::getKapasitas).sum();
        long slotNotRegister = totalKapasitas - totalPeserta;

        data.put("totalEvent", totalEvent);
        data.put("totalPeserta", totalPeserta);
        data.put("totalEventDone", totalEventDone);
        data.put("totalEventIncoming", totalEventIncoming);
        data.put("totalPesertaScanned", totalPesertaScanned);
        data.put("totalPesertaNotScanned", totalPesertaNotScanned);
        data.put("slotNotRegister", slotNotRegister);
        data.put("eventThisMonth", totalEventThisMonth);
        data.put("listEventThisMonth", eventsThisMonts);

        customeResponse.setData(data);

        return customeResponse;
    }

    public EventsDto mapToDto(Events events){
        EventsDto eventsDto = new EventsDto();

        eventsDto.setId(events.getId());
        eventsDto.setNama(events.getNama());
        eventsDto.setTempat(events.getTempat());
        eventsDto.setStart_date(events.getStart_date());
        eventsDto.setEnd_date(events.getEnd_date());
        eventsDto.setLengthofevent(events.getLengthofevent());
        eventsDto.setKapasitas(events.getKapasitas());
        eventsDto.setKeterangan(events.getKeterangan());
        eventsDto.setEventpathfiledesc(events.getEventpathfiledesc());
        eventsDto.setStatus(events.getStatus());
        eventsDto.setLinkregistration(events.getLinkregistration());
        eventsDto.setCreatedAt(events.getCreatedAt());
        eventsDto.setUpdatedAt(events.getUpdatedAt());

        return eventsDto;
    }

    public Events mapToEntity(EventsDto eventsDto){
        Events events = new Events();

        events.setId(eventsDto.getId());
        events.setNama(eventsDto.getNama());
        events.setTempat(eventsDto.getTempat());
        events.setStart_date(eventsDto.getStart_date());
        events.setEnd_date(eventsDto.getEnd_date());
        events.setLengthofevent(eventsDto.getLengthofevent());
        events.setKapasitas(eventsDto.getKapasitas());
        events.setKeterangan(eventsDto.getKeterangan());
        events.setEventpathfiledesc(eventsDto.getEventpathfiledesc());
        events.setStatus(eventsDto.getStatus());
        events.setLinkregistration(eventsDto.getLinkregistration());

        return events;
    }

}
