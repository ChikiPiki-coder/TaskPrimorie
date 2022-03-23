package com.banktask.taskforbank.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class PaymentDTO {
    private int userId;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private String comment;


    public PaymentDTO(int userId, LocalDate paymentDate, BigDecimal amount, String comment) {
        this.userId = userId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.comment = comment;
    }
}
