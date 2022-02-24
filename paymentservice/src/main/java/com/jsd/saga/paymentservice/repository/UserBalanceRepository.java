package com.jsd.saga.paymentservice.repository;

import com.jsd.saga.paymentservice.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Integer> {
}
