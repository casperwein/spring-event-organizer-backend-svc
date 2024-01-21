package com.eventorganizer.app.service;

import com.eventorganizer.app.entity.FormRegisterField;
import com.eventorganizer.app.payload.CustomeResponse;
import com.eventorganizer.app.payload.FormRegisterFieldDto;

import java.util.List;

public interface FormRegisterService {
    CustomeResponse createNewForm(FormRegisterFieldDto formRegisterFieldDto, long eventId);
    CustomeResponse  getAllFieldEligible(long eventId);
    List<FormRegisterFieldDto> getAllFormRegister();
    FormRegisterFieldDto getFieldRegisterByeventid(long eventId);
}
