package com.eventorganizer.app.service;

import com.eventorganizer.app.entity.Events;
import com.eventorganizer.app.payload.CustomeResponse;
import com.eventorganizer.app.payload.EventsDto;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

public interface EventsService {
    EventsDto createEvents(EventsDto eventsDto);
    EventsDto updateEvent(EventsDto eventsDto, long id);
    List<EventsDto> getAllEvents();
    EventsDto getEventsById(long id);
    String deleteEvent(long id);
    CustomeResponse getEventDetails(long eventId);
    CustomeResponse getEventsForDashboard();
    Events getEventDocPath(long eventId);

}
