package com.knr.hospital.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.knr.hospital.entity.Medical_Record;

import jakarta.validation.Valid;

public interface Medical_Record_Service {

	Medical_Record save(@Valid Medical_Record medical_Record);

	Page<Medical_Record> getAllMedicalRecords(int page, int size);

	Medical_Record getMedicalRecordById(Integer recordId);

	Medical_Record updateMedicalRecordById(Integer recordId, Medical_Record medical_Record);

	ResponseEntity<?> deleteMedicalRecordById(Integer recordId);

}
