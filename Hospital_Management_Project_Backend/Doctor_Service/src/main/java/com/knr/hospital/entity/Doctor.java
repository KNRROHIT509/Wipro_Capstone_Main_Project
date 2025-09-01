package com.knr.hospital.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorId;

    @NotBlank(message = "Doctor Name is mandatory")
    @Column(name = "doctor_name")
    private String doctorName;

    @NotBlank(message = "Doctor's Specialization is mandatory")
    @Column(name = "specialization", nullable = false)
    private String specialization;

    @NotBlank(message = "Contact Number is mandatory")
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact Number must be 10 digits")
    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @NotBlank(message = "Doctor's Email is required")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;
}
