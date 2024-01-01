package com.eventorganizer.app.repository;

import com.eventorganizer.app.entity.FormRegisterMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FormRegisterMasterRepository extends JpaRepository<FormRegisterMaster, Long> {
    @Query("SELECT e.value FROM FormRegisterMaster e WHERE e.param IN :values ORDER BY e.id ASC")
    List<String> getValueValidField(@Param("values") List<String> values);
}
