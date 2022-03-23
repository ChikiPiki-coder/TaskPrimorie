package com.banktask.taskforbank.models.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CommissionResponse {
    private int commissionPercent;
    private BigDecimal commissionAmount;

}
