package com.banktask.taskforbank.services;

import com.banktask.taskforbank.models.PaymentDTO;
import com.banktask.taskforbank.models.responce.CommissionResponse;
import com.banktask.taskforbank.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
class CommissionServiceTest {

    @Autowired
    private CommissionService commissionService;

    @Test
    void commissionCalculate() {
        PaymentDTO paymentDTOForTest = new PaymentDTO(3, LocalDate.of(2024, 3, 21), new BigDecimal(100), "Тест добавления платежа в течение месяца");
        CommissionResponse commissionResponseForTest = new CommissionResponse(5, new BigDecimal(5));
        Assert.assertEquals(commissionResponseForTest, commissionService.commissionCalculate(paymentDTOForTest));
        paymentDTOForTest = new PaymentDTO(4, LocalDate.of(2025, 4, 21), new BigDecimal(100000), "Тест при котором идет проверка, при одинаковом месяце, но разных годах, будет ли посчитаны проценты правильно");
        commissionResponseForTest = new CommissionResponse(5, new BigDecimal(5000));
        Assert.assertEquals(commissionResponseForTest, commissionService.commissionCalculate(paymentDTOForTest));
        paymentDTOForTest = new PaymentDTO(2, LocalDate.of(2024, 5, 2), new BigDecimal(1000), "Тест правильности вычисления процентов при единственном платеже в месяце");
        commissionResponseForTest = new CommissionResponse(1, new BigDecimal(10));
        Assert.assertEquals(commissionResponseForTest, commissionService.commissionCalculate(paymentDTOForTest));
        paymentDTOForTest = new PaymentDTO(2, LocalDate.of(2024, 5, 3), new BigDecimal(9000), "Тест правильности вычисления процентов при втором платеже, который стоит на границе смены платежей");
        commissionResponseForTest = new CommissionResponse(3, new BigDecimal(270));
        Assert.assertEquals(commissionResponseForTest, commissionService.commissionCalculate(paymentDTOForTest));

    }
}