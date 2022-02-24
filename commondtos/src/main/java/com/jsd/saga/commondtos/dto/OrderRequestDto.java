package com.jsd.saga.commondtos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    private int userId;
    private int productId;
    private double amount;
    private int orderId;
}
