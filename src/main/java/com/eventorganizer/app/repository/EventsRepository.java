package com.eventorganizer.app.repository;

import com.eventorganizer.app.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;

    public interface EventsRepository extends JpaRepository<Events, Long> {
        Events findEventById(long id);
        Events findBykapasitas(long id);
    @Query("SELECT COUNT(*) FROM Events WHERE status = 'DONE'")
    long getTotalEventDone();
    @Query("SELECT COUNT(*) FROM Events WHERE status = 'OPEN'")
    long getTotalEventIncoming();
}
