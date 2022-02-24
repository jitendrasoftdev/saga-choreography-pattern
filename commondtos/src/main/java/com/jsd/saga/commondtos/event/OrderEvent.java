package com.jsd.saga.commondtos.event;

import com.jsd.saga.commondtos.dto.OrderRequestDto;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
public class OrderEvent implements Event {
    private  UUID uuid = UUID.randomUUID();
    private Date eventDate = new Date();
    private OrderRequestDto orderRequestDto;
    private OrderStatus orderStatus;

    @Override
    public UUID getEventId() {
        return this.uuid ;
    }

    @Override
    public Date getEventDate() {
        return this.eventDate;
    }

    public OrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        this.orderRequestDto = orderRequestDto;
        this.orderStatus = orderStatus;
    }
}
