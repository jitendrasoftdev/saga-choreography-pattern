package com.jsd.saga.paymentservice.repository;

import com.jsd.saga.paymentservice.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionRepository extends JpaRepository<UserTransaction, Integer> {
}
