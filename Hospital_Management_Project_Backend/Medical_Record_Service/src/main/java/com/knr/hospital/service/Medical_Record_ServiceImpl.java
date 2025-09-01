package com.knr.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.knr.hospital.entity.Medical_Record;
import com.knr.hospital.repository.Medical_Record_Repository;

import jakarta.validation.Valid;

@Service
public class Medical_Record_ServiceImpl implements Medical_Record_Service {
	
	@Autowired
	private Medical_Record_Repository medical_Record_Repository;

	@Override
	public Medical_Record save(@Valid Medical_Record medical_Record) {
		// TODO Auto-generated method stub
		return medical_Record_Repository.save(medical_Record);
	}

	@Override
	public Page<Medical_Record> getAllMedicalRecords(int page, int size) {
		// TODO Auto-generated method stub
		return medical_Record_Repository.findAll(PageRequest.of(page, size));
	}

	@Override
	public Medical_Record getMedicalRecordById(Integer recordId) {
		// TODO Auto-generated method stub
		return medical_Record_Repository.findById(recordId).orElseThrow(()->new RuntimeException("Mediacal Record ID with "+recordId+" not found"));
	}

	@Override
	public Medical_Record updateMedicalRecordById(Integer recordId, Medical_Record medical_Record) {
		// TODO Auto-generated method stub
		Medical_Record updated=medical_Record_Repository.findById(recordId).orElseThrow(()->new RuntimeException("Medical Record ID with "+recordId+" not found"));
		updated.setDoctorId(medical_Record.getDoctorId());
		updated.setDescription(medical_Record.getDescription());
		updated.setPatientId(medical_Record.getPatientId());
		updated.setTreatedAt(medical_Record.getTreatedAt());
		updated.setMedicines(medical_Record.getMedicines());
		updated.setRevisit(medical_Record.getRevisit());
		return medical_Record_Repository.save(updated);
	}

	@Override
	public ResponseEntity<?> deleteMedicalRecordById(Integer recordId) {
		// TODO Auto-generated method stub
		medical_Record_Repository.deleteById(recordId);
		return ResponseEntity.ok("Medical Record ID with "+recordId+" deleted successfully");
	}
}
