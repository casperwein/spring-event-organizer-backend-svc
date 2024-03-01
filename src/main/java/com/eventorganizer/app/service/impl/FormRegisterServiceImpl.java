package com.eventorganizer.app.service.impl;

import com.eventorganizer.app.entity.FormRegisterField;
import com.eventorganizer.app.payload.CustomeResponse;
import com.eventorganizer.app.payload.FormRegisterFieldDto;
import com.eventorganizer.app.repository.FormRegisterFieldRepository;
import com.eventorganizer.app.repository.FormRegisterMasterRepository;
import com.eventorganizer.app.service.FormRegisterService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FormRegisterServiceImpl implements FormRegisterService {
    private FormRegisterFieldRepository formFieldRepo;
    private FormRegisterMasterRepository formMasterRepo;

    public FormRegisterServiceImpl(FormRegisterFieldRepository formFieldRepo, FormRegisterMasterRepository formMasterRepo) {
        this.formFieldRepo = formFieldRepo;
        this.formMasterRepo = formMasterRepo;
    }

    @Override
    public CustomeResponse createNewForm(FormRegisterFieldDto formRegisterFieldDto, long eventId) {
        FormRegisterField fields = formFieldRepo.getFormFieldByeventid(eventId);
        CustomeResponse customeResponse = new CustomeResponse();
        if(fields != null){
            LocalDateTime date = LocalDateTime.now();
            formRegisterFieldDto.setCreatedAt(fields.getCreatedAt());
            formRegisterFieldDto.setUpdatedAt(date);

            FormRegisterField fieldsUpdated = mapToEntity(formRegisterFieldDto, fields);
            formFieldRepo.save(fieldsUpdated);
            customeResponse.setData("Update Form Succesfully!");
        } else {
            FormRegisterField fieldEntity = new FormRegisterField();
            FormRegisterField fieldRequired = mapToEntity(formRegisterFieldDto, fieldEntity);
            fieldRequired.setEventid(eventId);
            FormRegisterField fieldRegistered = formFieldRepo.save(fieldRequired);
            FormRegisterFieldDto response = mapToDto(fieldRegistered);
            customeResponse.setData(response);
        }

        return customeResponse;
    }

    @Override
    public CustomeResponse getAllFieldEligible(long eventId) {
        FormRegisterField formField = formFieldRepo.getFormFieldByeventid(eventId);
        List<String> fieldsEligible = new ArrayList<>();
        Field[] fields = formField.getClass().getDeclaredFields();

        for(Field field : fields){
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(formField);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if(value != null && value.toString().equalsIgnoreCase(("true"))){
                fieldsEligible.add(field.getName());
            }
        }
        List<String> formMaster = formMasterRepo.getValueValidField(fieldsEligible);
        CustomeResponse customeResponse = new CustomeResponse();
        Map<String, Object> data = new HashMap<>();
        data.put("fieldEligible", fieldsEligible);
        data.put("fieldData", formMaster);
        customeResponse.setData(data);

        return customeResponse;
    }

    @Override
    public List<FormRegisterFieldDto> getAllFormRegister() {
        List<FormRegisterField> formRegisterField = formFieldRepo.findAll();

        return formRegisterField.stream().map((field) -> mapToDto(field)).collect(Collectors.toList());
    }

    @Override
    public FormRegisterFieldDto getFieldRegisterByeventid(long eventId) {
        FormRegisterField formRegisterField = formFieldRepo.getFormFieldByeventid(eventId);
        FormRegisterFieldDto formRegisterFieldDto = mapToDto(formRegisterField);

        return formRegisterFieldDto;
    }


    public FormRegisterFieldDto mapToDto(FormRegisterField entity){
        FormRegisterFieldDto fieldDto = new FormRegisterFieldDto();

        fieldDto.setId(entity.getId());
        fieldDto.setNama(entity.getNama());
        fieldDto.setEmail(entity.getEmail());
        fieldDto.setAlamat(entity.getAlamat());
        fieldDto.setTelepon(entity.getTelepon());
        fieldDto.setLos(entity.getLos());
        fieldDto.setNik(entity.getNik());
        fieldDto.setEventid(entity.getEventid());
        fieldDto.setMediasosial(entity.getMediasosial());
        fieldDto.setNo_kk(entity.getNo_kk());
        fieldDto.setAgama(entity.getAgama());
        fieldDto.setKec(entity.getKec());
        fieldDto.setKota(entity.getKota());
        fieldDto.setProvinsi(entity.getProvinsi());
        fieldDto.setKelurahan(entity.getKelurahan());
        fieldDto.setCreatedAt(entity.getCreatedAt());
        fieldDto.setUpdatedAt(entity.getUpdatedAt());
        fieldDto.setTanggal_lahir(entity.getTanggal_lahir());

        return fieldDto;
    }

    public FormRegisterField mapToEntity(FormRegisterFieldDto fieldDto, FormRegisterField entity){
        entity.setNama(fieldDto.getNama());
        entity.setEmail(fieldDto.getEmail());
        entity.setAlamat(fieldDto.getAlamat());
        entity.setTelepon(fieldDto.getTelepon());
        entity.setLos(fieldDto.getLos());
        entity.setNik(fieldDto.getNik());
        entity.setMediasosial(fieldDto.getMediasosial());
        entity.setNo_kk(fieldDto.getNo_kk());
        entity.setAgama(fieldDto.getAgama());
        entity.setKec(fieldDto.getKec());
        entity.setTanggal_lahir(fieldDto.getTanggal_lahir());
        entity.setKota(fieldDto.getKota());
        entity.setProvinsi(fieldDto.getProvinsi());
        entity.setKelurahan(fieldDto.getKelurahan());
        entity.setCreatedAt(fieldDto.getCreatedAt());
        entity.setUpdatedAt(fieldDto.getUpdatedAt());

        return entity;
    }
}
