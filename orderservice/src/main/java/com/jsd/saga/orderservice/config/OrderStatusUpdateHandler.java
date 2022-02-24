package com.jsd.saga.orderservice.config;

import com.jsd.saga.commondtos.dto.OrderRequestDto;
import com.jsd.saga.commondtos.event.OrderStatus;
import com.jsd.saga.commondtos.event.PaymentStatus;
import com.jsd.saga.orderservice.entity.PurchaseOrder;
import com.jsd.saga.orderservice.repository.OrderRepository;
import com.jsd.saga.orderservice.service.OrderStatusPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.util.function.Consumer;

@Configuration
public class OrderStatusUpdateHandler {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public void updateOrder(int orderId, Consumer<PurchaseOrder> consumer) {
        orderRepository.findById(orderId).ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(PurchaseOrder purchaseOrder) {
        boolean isPaymentCompleted = PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
        OrderStatus orderStatus = isPaymentCompleted ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        purchaseOrder.setOrderStatus(orderStatus);
        if(!isPaymentCompleted) {
            orderStatusPublisher.publishOrderEvent(convertEntityToDto(purchaseOrder), orderStatus);
        }
    }

    public OrderRequestDto convertEntityToDto(PurchaseOrder purchaseOrder) {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderId(purchaseOrder.getId());
        orderRequestDto.setUserId(purchaseOrder.getUserId());
        orderRequestDto.setAmount(purchaseOrder.getAmount());
        orderRequestDto.setProductId(purchaseOrder.getProductId());
        return orderRequestDto;
    }
}
