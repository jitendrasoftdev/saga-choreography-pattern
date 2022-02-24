package com.jsd.saga.paymentservice.service;

import com.jsd.saga.commondtos.dto.OrderRequestDto;
import com.jsd.saga.commondtos.dto.PaymentRequestDto;
import com.jsd.saga.commondtos.event.OrderEvent;
import com.jsd.saga.commondtos.event.PaymentEvent;
import com.jsd.saga.commondtos.event.PaymentStatus;
import com.jsd.saga.paymentservice.entity.UserBalance;
import com.jsd.saga.paymentservice.entity.UserTransaction;
import com.jsd.saga.paymentservice.repository.UserBalanceRepository;
import com.jsd.saga.paymentservice.repository.UserTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentService {

    @Autowired
    UserBalanceRepository userBalanceRepository;

    @Autowired
    UserTransactionRepository userTransactionRepository;

    @PostConstruct
    public void initUserBalanceInDB() {
        userBalanceRepository.saveAll(Stream.of(
                new UserBalance(101, 5000),
                new UserBalance(102, 3000),
                new UserBalance(103, 4200),
                new UserBalance(104, 20000),
                new UserBalance(105, 999)
        ).collect(Collectors.toList()));
    }

    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(),
                orderRequestDto.getUserId(), orderRequestDto.getAmount());
        return userBalanceRepository.findById(orderRequestDto.getUserId())
                .filter(ub -> ub.getAmount() > orderRequestDto.getAmount())
                .map(ub -> {
                    ub.setAmount(ub.getAmount() - orderRequestDto.getAmount());
                    userTransactionRepository.save(new UserTransaction(
                            orderRequestDto.getOrderId(),
                            orderRequestDto.getUserId(),
                            orderRequestDto.getAmount()));
                    return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));
    }

    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
                .ifPresent(ut -> {
                    userTransactionRepository.delete(ut);
                    userTransactionRepository.findById(ut.getUserId())
                            .ifPresent(ub->ub.setAmount(ub.getAmount() + ut.getAmount()));
                });
    }
}
