package com.knr.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.knr.hospital.entity.Billing;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Integer> {

}
