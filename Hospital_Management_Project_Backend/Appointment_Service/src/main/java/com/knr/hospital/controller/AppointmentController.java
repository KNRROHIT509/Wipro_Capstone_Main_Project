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

import com.knr.hospital.entity.Appointment;
import com.knr.hospital.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	
	@PostMapping
	private Appointment createAppointment(@Valid @RequestBody Appointment appointment) {
		return appointmentService.save(appointment);
	}
	
	@GetMapping("/allAppointments")
	private Page<Appointment> getAllAppointments(@RequestParam int page , @RequestParam int size){
		return appointmentService.getAllAppointments(page,size);
	}
	
	@GetMapping("/getAppointment/{appointmentId}")
	private Appointment getAppointmentById(@PathVariable Integer appointmentId) {
		return appointmentService.getAppointmentById(appointmentId);
	}
	
	@PatchMapping("/update/{appointmentId}")
	private Appointment updateAppointmentById(@PathVariable Integer appointmentId, @RequestBody Appointment appointment) {
		return appointmentService.updateAppointmentById(appointmentId,appointment);
	}
	
	@DeleteMapping("/delete/{appointmentId}")
	private ResponseEntity<?> deleteAppointmentById(@PathVariable Integer appointmentId){
		return appointmentService.deleteAppointmentById(appointmentId);
	}
}
