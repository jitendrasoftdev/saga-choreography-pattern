package com.jsd.saga.orderservice.config;

import com.jsd.saga.commondtos.event.PaymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.jsd.saga.commondtos.dto.PaymentRequestDto;


import java.util.function.Consumer;

@Configuration
public class EventConsumerConfig {

    @Autowired
    private OrderStatusUpdateHandler handler;

    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer() {
        //listen payment-event topic
        //Check payment status
        //if payment status completed -> complete the order
        //if payment status failed -> cancel the order
        return (payment) -> handler.updateOrder(payment.getPaymentRequestDto().getOrderId(),
                po -> po.setPaymentStatus(payment.getPaymentStatus()));
    }
}
