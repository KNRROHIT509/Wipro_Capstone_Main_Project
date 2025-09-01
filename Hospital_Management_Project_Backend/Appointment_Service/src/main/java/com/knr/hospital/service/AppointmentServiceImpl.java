package com.knr.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.knr.hospital.entity.Appointment;
import com.knr.hospital.repository.AppointmentRepository;

import jakarta.validation.Valid;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	
	@Autowired
	private AppointmentRepository appointmentRepository;

	@Override
	public Appointment save(@Valid Appointment appointment) {
		// TODO Auto-generated method stub
		return appointmentRepository.save(appointment);
	}

	@Override
	public Page<Appointment> getAllAppointments(int page, int size) {
		// TODO Auto-generated method stub
		return appointmentRepository.findAll(PageRequest.of(page, size));
	}

	@Override
	public Appointment getAppointmentById(Integer appointmentId) {
		// TODO Auto-generated method stub
		return appointmentRepository.findById(appointmentId).orElseThrow(()-> new RuntimeException("Appointment ID with "+appointmentId+" not found"));
	}

	@Override
	public Appointment updateAppointmentById(Integer appointmentId, Appointment appointment) {
		// TODO Auto-generated method stub
		Appointment updated=appointmentRepository.findById(appointmentId).orElseThrow(()->new RuntimeException("Appointment ID with "+appointmentId+" not found"));
		updated.setPatientId(appointment.getPatientId());
		updated.setDoctorId(appointment.getDoctorId());
		updated.setAppointmentDate(appointment.getAppointmentDate());
		updated.setStatus(appointment.getStatus());
		updated.setAppointmentType(appointment.getAppointmentType());
		return appointmentRepository.save(updated);
	}

	@Override
	public ResponseEntity<?> deleteAppointmentById(Integer appointmentId) {
		// TODO Auto-generated method stub
		appointmentRepository.deleteById(appointmentId);
		return ResponseEntity.ok("Appointment ID with "+appointmentId+" deleted successfully");
	}
	
	

}
