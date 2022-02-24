package com.jsd.saga.orderservice.service;

import com.jsd.saga.commondtos.dto.OrderRequestDto;
import com.jsd.saga.commondtos.event.OrderEvent;
import com.jsd.saga.commondtos.event.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

//you can also annotate with @Component as well as instead of service
@Service
public class OrderStatusPublisher {

    @Autowired
    private Sinks.Many<OrderEvent> orderSinks;

    public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        OrderEvent orderEvent = new OrderEvent(orderRequestDto,orderStatus);
        orderSinks.tryEmitNext(orderEvent);
    }
}
