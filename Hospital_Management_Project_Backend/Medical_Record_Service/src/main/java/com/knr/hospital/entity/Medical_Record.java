package com.knr.hospital.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medical_Record {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recordId;
	
	@NotNull(message="Patient ID is mandatory")
	@Column(name="patient_id")
	private Integer patientId;
	
	@NotBlank(message="Description about medical record is mandatory")
	@Column(name="description")
	private String description;
	
	@NotNull(message="Doctor ID is mandatory")
	@Column(name="doctor_id")
	private Integer doctorId;
	
	@NotNull(message="When patient is treated by doctor is required")
	@Column(name="treated_at")
	private LocalDateTime treatedAt;
	
	@NotEmpty(message="Medicines list is mandatory")
	@Column(name="medicines")
	private List<String> medicines;
	
	@Column(name="revisit")
	private LocalDate revisit;

}
