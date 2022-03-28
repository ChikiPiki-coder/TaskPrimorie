package com.banktask.taskforbank.services;

import com.banktask.taskforbank.entity.Payment;

import com.banktask.taskforbank.models.PaymentDTO;
import com.banktask.taskforbank.models.responce.CommissionResponse;
import com.banktask.taskforbank.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommissionService {

    private final PaymentRepository paymentRepository;

    public CommissionResponse commissionCalculate(PaymentDTO paymentDTO) {
        saveToBD(paymentDTO);
        List<Payment> userPayments = paymentRepository.getPaymentsByUserId(paymentDTO.getUserId());

        if (userPayments != null && userPayments.size() != 0) {
            LocalDate currentDate = paymentDTO.getPaymentDate();

            List<Payment> currentMonthPayments = findPaymentsByCurrentMonth(userPayments, currentDate);

            return processCommission(currentMonthPayments, paymentDTO);
        }

        return new CommissionResponse(1,
                paymentDTO.getAmount()
                        .multiply(new BigDecimal(1))
                        .divide(new BigDecimal(100))
        );
    }

    private void saveToDB(PaymentDTO payment){
        Payment paymentEntity = new Payment();
        paymentEntity.setAmount(payment.getAmount());
        paymentEntity.setPaymentDate(payment.getPaymentDate());
        paymentEntity.setComment(payment.getComment());
        paymentEntity.setUserId(payment.getUserId());
        paymentRepository.save(paymentEntity);
    }
    private List<Payment> findPaymentsByCurrentMonth(List<Payment> allPayments, LocalDate currentDate) {
        LocalDate firstDayOfMonth = currentDate.minusDays(currentDate.getDayOfMonth() - 1);

        return allPayments.stream().filter(payment ->
                payment.getPaymentDate().isAfter(firstDayOfMonth)
                        && payment.getPaymentDate().isBefore(firstDayOfMonth.plusMonths(1))
        ).collect(Collectors.toList());
    }

    private CommissionResponse processCommission(List<Payment> payments, PaymentDTO paymentDTO) {
        BigDecimal summary = payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        int percent;

        if (summary.intValue() < 10000 && summary.intValue() > 0) {
            percent = 1;
        } else if (summary.intValue() >= 10000 && summary.intValue() < 100_000) {
            percent = 3;
        } else {
            percent = 5;
        }

        BigDecimal bigDecimal=paymentDTO.
                getAmount()
                .multiply(new BigDecimal(percent))
                .divide(new BigDecimal(100));
        return new CommissionResponse(percent, paymentDTO.
                getAmount()
                .multiply(new BigDecimal(percent))
                .divide(new BigDecimal(100))
        );
    }
}
