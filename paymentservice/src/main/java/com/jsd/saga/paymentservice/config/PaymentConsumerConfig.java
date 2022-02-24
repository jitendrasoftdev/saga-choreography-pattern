package com.jsd.saga.paymentservice.config;

import com.jsd.saga.commondtos.event.OrderEvent;
import com.jsd.saga.commondtos.event.OrderStatus;
import com.jsd.saga.commondtos.event.PaymentEvent;
import com.jsd.saga.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class PaymentConsumerConfig {

    @Autowired
    private PaymentService paymentService;

    @Bean
    public Function<Flux<OrderEvent>,Flux<PaymentEvent>> paymentProcessor() {
        return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
    }

    private Mono<PaymentEvent> processPayment(OrderEvent orderEvent) {
        //Get User Id and check payment availability
        // if have sufficient balance, Payment completed and deduct it from db.
        //if Payment not sufficient amount, cancel the oder event and update the amount in db.
        if(OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus()))
            return Mono.fromSupplier(() -> this.paymentService.newOrderEvent(orderEvent));
        else
            return Mono.fromRunnable(() -> this.paymentService.cancelOrderEvent(orderEvent));
    }
}
