package com.knr.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.knr.hospital.entity.Medical_Record;
import com.knr.hospital.service.Medical_Record_Service;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicalrecords")
public class Medical_Record_Controller {
	
	@Autowired
	private Medical_Record_Service medical_Record_Service;
	
	@PostMapping
	private Medical_Record createMedical_Record_(@Valid @RequestBody Medical_Record medical_Record) {
		return medical_Record_Service.save(medical_Record);
	}
	
	@GetMapping("/allMedicalRecords")
	private Page<Medical_Record> getAllMedicalRecords(@RequestParam int page , @RequestParam int size){
		return medical_Record_Service.getAllMedicalRecords(page,size);
	}
	
	@GetMapping("/getMedicalRecord/{recordId}")
	private Medical_Record getMedical_Record_ById(@PathVariable Integer recordId) {
		return medical_Record_Service.getMedicalRecordById(recordId);
	}
	
	@PatchMapping("/update/{recordId}")
	private Medical_Record updateMedical_Record_ById(@PathVariable Integer recordId, @Valid @RequestBody Medical_Record medical_Record) {
		return medical_Record_Service.updateMedicalRecordById(recordId,medical_Record);
	}
	
	@DeleteMapping("/delete/{recordId}")
	private ResponseEntity<?> deleteMedical_Record_ById(@PathVariable Integer recordId){
		return medical_Record_Service.deleteMedicalRecordById(recordId);
	}
}
