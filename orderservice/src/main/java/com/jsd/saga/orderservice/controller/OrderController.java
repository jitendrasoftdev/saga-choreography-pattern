package com.jsd.saga.orderservice.controller;

import com.jsd.saga.commondtos.dto.OrderRequestDto;
import com.jsd.saga.orderservice.entity.PurchaseOrder;
import com.jsd.saga.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public PurchaseOrder createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }

    @RequestMapping
    public List<PurchaseOrder> getOrders() {
        return orderService.getAllOrder();
    }
}
