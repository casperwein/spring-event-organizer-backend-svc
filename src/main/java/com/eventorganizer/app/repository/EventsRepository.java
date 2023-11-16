package com.eventorganizer.app.repository;

import com.eventorganizer.app.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepository extends JpaRepository<Events, Long> {
    Events findEventById(long id);

}
