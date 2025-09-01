package com.knr.hospital.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.knr.hospital.entity.Doctor;

import jakarta.validation.Valid;

public interface DoctorService {

	Doctor save(@Valid Doctor doctor);

	Page<Doctor> getAllDoctors(int page, int size);

	Doctor getDoctorById(Integer doctorId);

	Doctor updateDoctorById(Integer doctorId, Doctor doctor);

	ResponseEntity<?> deleteDoctorById(Integer doctorId);

}
