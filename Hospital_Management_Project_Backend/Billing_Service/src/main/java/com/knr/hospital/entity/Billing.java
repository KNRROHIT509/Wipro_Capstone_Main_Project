package com.knr.hospital.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Billing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billId;
	
	@NotNull(message="Patient ID is mandatory")
	@Column(name="patient_id")
    private Integer patientId;
    
	@NotNull(message="Appointment ID is mandatory")
	@Column(name="appointment_id")
	private Integer appointmentId;
	
	@Column(name="total_amount")
	@NotNull(message="	Bill is mandatory")
    private BigDecimal totalAmount; 
	
	@NotNull(message="Payment status is required")
	@Column(name="payment_status")
    private PayStatus paymentStatus;
	
	@NotNull(message="Bill date is required")
	@Column(name="bill_date")
    private LocalDateTime billDate;
}