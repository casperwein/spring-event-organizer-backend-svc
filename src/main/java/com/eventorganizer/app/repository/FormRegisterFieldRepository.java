package com.eventorganizer.app.repository;

import com.eventorganizer.app.entity.FormRegisterField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FormRegisterFieldRepository extends JpaRepository<FormRegisterField, Long> {
    FormRegisterField getFormFieldByeventid(long id);
}
