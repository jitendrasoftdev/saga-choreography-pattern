package com.jsd.saga.orderservice.service;

import com.jsd.saga.commondtos.dto.OrderRequestDto;
import com.jsd.saga.commondtos.event.OrderStatus;
import com.jsd.saga.orderservice.entity.PurchaseOrder;
import com.jsd.saga.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public PurchaseOrder createOrder(OrderRequestDto orderRequestDto) {
         PurchaseOrder order = orderRepository.save(convertDtoToEntity(orderRequestDto));
         orderRequestDto.setOrderId(order.getId());
         //produce kafka event with  status ORDER_CREATED
        orderStatusPublisher.publishOrderEvent(orderRequestDto,OrderStatus.ORDER_CREATED);
        return order;
    }

    public List<PurchaseOrder> getAllOrder() {
        return orderRepository.findAll();
    }


    private PurchaseOrder convertDtoToEntity(OrderRequestDto orderRequestDto) {
        PurchaseOrder po = new PurchaseOrder();
        po.setProductId(orderRequestDto.getProductId());
        po.setUserId(orderRequestDto.getUserId());
        po.setOrderStatus(OrderStatus.ORDER_CREATED);
        po.setAmount(orderRequestDto.getAmount());
        return po;
    }

}
