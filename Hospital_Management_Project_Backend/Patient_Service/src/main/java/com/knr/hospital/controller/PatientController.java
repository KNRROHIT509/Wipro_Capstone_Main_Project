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

import com.knr.hospital.entity.Patient;
import com.knr.hospital.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@PostMapping
	private Patient createDoctor(@Valid @RequestBody Patient patient) {
		return patientService.save(patient);
	}
	
	@GetMapping("/allPatients")
	private Page<Patient> getAllDoctors(@RequestParam int page , @RequestParam int size){
		return patientService.getAllPatients(page,size);
	}
	
	@GetMapping("/getPatient/{patientId}")
	private Patient getDoctorById(@PathVariable Integer patientId) {
		return patientService.getPatientById(patientId);
	}
	
	@PatchMapping("/update/{patientId}")
	private Patient updatePatientById(@PathVariable Integer patientId, @RequestBody Patient patient) {
		return patientService.updatePatientById(patientId,patient);
	}
	
	@DeleteMapping("/delete/{patientId}")
	private ResponseEntity<?> deletePatientById(@PathVariable Integer patientId){
		return patientService.deletePatientById(patientId);
	}

}
