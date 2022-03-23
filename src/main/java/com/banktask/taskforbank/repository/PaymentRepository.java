package com.banktask.taskforbank.repository;

import com.banktask.taskforbank.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> getPaymentsByUserId(int id);
}
