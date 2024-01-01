package com.eventorganizer.app.controller;

import com.eventorganizer.app.payload.CustomeResponse;
import com.eventorganizer.app.payload.FormRegisterFieldDto;
import com.eventorganizer.app.service.FormRegisterService;
import com.eventorganizer.app.util.Utils;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spring/eo/v1/form/")
public class FormRegisterController {
    private FormRegisterService formRegisterService;
    Utils utils = new Utils();

    public FormRegisterController(FormRegisterService formRegisterService) {
        this.formRegisterService = formRegisterService;
    }

    @PostMapping("/field/event/{id}")
    public ResponseEntity<CustomeResponse> createFormField(@RequestBody FormRegisterFieldDto formRegisterFieldDto, @PathVariable(name = "id") long eventId){
        CustomeResponse customeResponse = utils.customeResponses();
        CustomeResponse fieldForm = formRegisterService.createNewForm(formRegisterFieldDto, eventId);
        customeResponse.setData(fieldForm);
        return new ResponseEntity<>(customeResponse, HttpStatus.OK);
    }

    @GetMapping("/field/event/{id}/eligible")
    public ResponseEntity<CustomeResponse> getFieldEligbleByEventId(@PathVariable(value = "id") long eventId){
        CustomeResponse customeResponse = utils.customeResponses();
        List<String> dataField = formRegisterService.getAllFieldEligible(eventId);
        customeResponse.setData(dataField);
        return new ResponseEntity<>(customeResponse, HttpStatus.OK);
    }

    @GetMapping("/field/event/{id}")
    public ResponseEntity<CustomeResponse> getFieldByEventId(@PathVariable(value = "id") long eventId){
        CustomeResponse customeResponse = utils.customeResponses();
        FormRegisterFieldDto dataField = formRegisterService.getFieldRegisterByeventid(eventId);
        customeResponse.setData(dataField);
        return new ResponseEntity<>(customeResponse, HttpStatus.OK);
    }

    @GetMapping("/field")
        public ResponseEntity<CustomeResponse> getAllFormRegisterField(){
        CustomeResponse customeResponse = utils.customeResponses();
        List<FormRegisterFieldDto> formRegister = formRegisterService.getAllFormRegister();
        customeResponse.setData(formRegister);
        return new ResponseEntity<>(customeResponse, HttpStatus.OK);
    }
}
