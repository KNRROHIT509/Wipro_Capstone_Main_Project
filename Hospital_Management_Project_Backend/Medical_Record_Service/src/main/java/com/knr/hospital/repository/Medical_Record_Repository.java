package com.knr.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.knr.hospital.entity.Medical_Record;

@Repository
public interface Medical_Record_Repository extends JpaRepository<Medical_Record, Integer> {
	
}
