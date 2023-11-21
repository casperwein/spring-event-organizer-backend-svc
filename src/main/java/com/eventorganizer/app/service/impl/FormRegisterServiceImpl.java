package com.eventorganizer.app.service.impl;

import com.eventorganizer.app.entity.FormRegisterField;
import com.eventorganizer.app.payload.FormRegisterFieldDto;
import com.eventorganizer.app.repository.FormRegisterFieldRepository;
import com.eventorganizer.app.repository.FormRegisterMasterRepository;
import com.eventorganizer.app.service.FormRegisterService;
import org.springframework.stereotype.Service;

@Service
public class FormRegisterServiceImpl implements FormRegisterService {
    private FormRegisterFieldRepository formFieldRepo;

    public FormRegisterServiceImpl(FormRegisterFieldRepository formFieldRepo, FormRegisterMasterRepository formMasterRepo) {
        this.formFieldRepo = formFieldRepo;
        this.formMasterRepo = formMasterRepo;
    }

    private FormRegisterMasterRepository formMasterRepo;
    @Override
    public FormRegisterFieldDto createNewForm(FormRegisterFieldDto formRegisterFieldDto) {
        FormRegisterField fieldRequired = mapToEntity(formRegisterFieldDto);
        FormRegisterField fieldRegistered = formFieldRepo.save(fieldRequired);
        FormRegisterFieldDto response = mapToDto(fieldRegistered);
        return response;
    }

    @Override
    public FormRegisterFieldDto getAllFieldEligible(long eventId) {
//        FormRegisterField formField = formFieldRepo.findFieldByEventId(eventId);
//        FormRegisterFieldDto dataField = mapToDto(formField);
        return null;
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
        fieldDto.setMediasosial(entity.getMediasosial());
        fieldDto.setNo_kk(entity.getNo_kk());
        fieldDto.setAgama(entity.getAgama());
        fieldDto.setKec(entity.getKec());
        fieldDto.setKota(entity.getKota());
        fieldDto.setProvinsi(entity.getProvinsi());
        fieldDto.setKelurahan(entity.getKelurahan());
        fieldDto.setCreatedAt(entity.getCreatedAt());
        fieldDto.setUpdatedAt(entity.getUpdatedAt());

        return fieldDto;
    }

    public FormRegisterField mapToEntity(FormRegisterFieldDto fieldDto){
        FormRegisterField entity = new FormRegisterField();

        entity.setId(fieldDto.getId());
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
        entity.setKota(fieldDto.getKota());
        entity.setProvinsi(fieldDto.getProvinsi());
        entity.setKelurahan(fieldDto.getKelurahan());
        entity.setCreatedAt(fieldDto.getCreatedAt());
        entity.setUpdatedAt(fieldDto.getUpdatedAt());

        return entity;
    }
}
