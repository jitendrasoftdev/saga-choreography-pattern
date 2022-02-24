package com.jsd.saga.commondtos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {
    private int orderId;
    private int userId;
    private double amount;
}
