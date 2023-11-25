package com.eventorganizer.app.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerService {
    @Scheduled(fixedRate = 60000)
    public void runTask(){
        int number = 1;
        System.out.println("I am work now " + number);
    }
}
