package com.eventorganizer.app.controller;

import com.eventorganizer.app.payload.CustomeResponse;
import com.eventorganizer.app.payload.EventsDto;
import com.eventorganizer.app.service.EventsService;
import com.eventorganizer.app.util.Utils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/spring/eo/v1/event")
public class EventsController {
    private EventsService eventsService;
    Utils utils = new Utils();
    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @PostMapping
    public ResponseEntity<CustomeResponse> createNewEvent(@RequestPart EventsDto eventsDto, @RequestPart MultipartFile eventpathfiledesc){
        eventsDto.setFile(eventpathfiledesc);
        EventsDto event = eventsService.createEvents(eventsDto);

        CustomeResponse customeResponse = utils.customeResponses();
        List<EventsDto> eventsDtoList = new ArrayList<>();
        eventsDtoList.add(event);
        customeResponse.setData(eventsDtoList);
        customeResponse.setStatusCode(HttpServletResponse.SC_CREATED);

        return new ResponseEntity<>(customeResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CustomeResponse> getAllEvents(){
        CustomeResponse customeResponse = utils.customeResponses();
        List<EventsDto> eventsDtoList = eventsService.getAllEvents();
        customeResponse.setData(eventsDtoList);
        customeResponse.setStatusCode(HttpServletResponse.SC_OK);
        return new ResponseEntity<>(customeResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomeResponse> getEventById(@PathVariable(name = "id") long id){
        CustomeResponse customeResponse = utils.customeResponses();
        EventsDto eventsDto = eventsService.getEventsById(id);
        List<EventsDto> eventsDtoList = new ArrayList<>();
        eventsDtoList.add(eventsDto);
        customeResponse.setData(eventsDtoList);
        customeResponse.setStatusCode(HttpServletResponse.SC_OK);
        return new ResponseEntity<>(customeResponse, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomeResponse> updateEvent(@PathVariable(name = "id") long id, @RequestBody EventsDto eventsDto){
        EventsDto event = eventsService.updateEvent(eventsDto, id);
        System.out.println(id);
        CustomeResponse customeResponse = utils.customeResponses();
        List<EventsDto> eventsDtoList = new ArrayList<>();
        eventsDtoList.add(event);
        customeResponse.setData(eventsDtoList);
        customeResponse.setStatusCode(HttpServletResponse.SC_OK);
        return new ResponseEntity<>(customeResponse, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<CustomeResponse> getEventDetail(@PathVariable(name = "id") long eventId){
        CustomeResponse  customeResponse = eventsService.getEventDetails(eventId);
        customeResponse.setStatusCode(HttpServletResponse.SC_OK);
        return new ResponseEntity<>(customeResponse, HttpStatus.OK);
    }

    @GetMapping("/dataForDashboard")
    public ResponseEntity<CustomeResponse> getDataForDashboard(){
        CustomeResponse customeResponse = utils.customeResponses();
        customeResponse.setData(eventsService.getEventsForDashboard().getData());
        return new ResponseEntity<>(customeResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public CustomeResponse deleteEvents(@PathVariable(name = "id") long id){
        String event = eventsService.deleteEvent(id);
        CustomeResponse customeResponse = utils.customeResponses();
        List list = new ArrayList<>();

        customeResponse.setMessage(event);
        customeResponse.setData(list);
        customeResponse.setStatusCode(HttpServletResponse.SC_OK);

        return customeResponse;
    }
}
