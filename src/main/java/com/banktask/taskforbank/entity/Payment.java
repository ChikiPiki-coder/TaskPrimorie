package com.banktask.taskforbank.entity;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name="payment", schema = "public")
public class Payment {
    @Id
    @Column(name="id")
    @GeneratedValue
    Integer id;

   @Column(name="user_id")
    private int userId;

    @Column(name="payment_date")
    private LocalDate paymentDate;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="comment")
    private String comment;


}
