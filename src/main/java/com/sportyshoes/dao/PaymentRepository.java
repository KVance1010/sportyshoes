package com.sportyshoes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sportyshoes.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
