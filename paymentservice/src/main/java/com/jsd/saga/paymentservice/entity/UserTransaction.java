package com.jsd.saga.paymentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserTransaction {
    @Id
    private int orderId;
    private int userId;
    private double amount;

}
