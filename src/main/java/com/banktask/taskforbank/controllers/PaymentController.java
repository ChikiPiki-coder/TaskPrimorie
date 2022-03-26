package com.banktask.taskforbank.controllers;

import com.banktask.taskforbank.entity.Payment;
import com.banktask.taskforbank.models.PaymentDTO;
import com.banktask.taskforbank.models.responce.CommissionResponse;
import com.banktask.taskforbank.repository.PaymentRepository;
import com.banktask.taskforbank.services.CommissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final CommissionService commissionService;

    @PostMapping("/payment")
    public ResponseEntity<CommissionResponse> processPayment(@RequestBody PaymentDTO payment) {
        return new ResponseEntity<>(commissionService.commissionCalculate(payment), HttpStatus.OK);
    }
}
