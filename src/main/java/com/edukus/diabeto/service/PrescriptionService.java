package com.edukus.diabeto.service;

import com.edukus.diabeto.presentation.dto.PrescriptionDto;

import java.util.List;

public interface PrescriptionService {

    void savePrescription(PrescriptionDto prescriptionDto);
    void updatePrescription(Long id,PrescriptionDto newPrescription);
    void disablePrescription(Long id);
    List <PrescriptionDto> findActivePrescriptionByUserEmail(String email,Boolean isActive,int roleId);


}
