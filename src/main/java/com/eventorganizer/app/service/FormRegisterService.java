package com.eventorganizer.app.service;

import com.eventorganizer.app.payload.FormRegisterFieldDto;

import java.util.List;

public interface FormRegisterService {
    FormRegisterFieldDto createNewForm(FormRegisterFieldDto formRegisterFieldDto);
    FormRegisterFieldDto getAllFieldEligible(long eventId);
}
