package com.jsd.saga.commondtos.dto;

import com.jsd.saga.commondtos.event.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private int userId;
    private int productId;
    private double amount;
    private int orderId;
    private OrderStatus orderStatus;
}
