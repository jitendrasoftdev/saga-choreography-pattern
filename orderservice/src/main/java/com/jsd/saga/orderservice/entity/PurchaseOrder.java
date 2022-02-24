package com.jsd.saga.orderservice.entity;

import com.jsd.saga.commondtos.event.OrderStatus;
import com.jsd.saga.commondtos.event.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {
    @Id
    @GeneratedValue
    private int id;
    private int userId;
    private int productId;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
