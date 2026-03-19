package com.example.paymentservice.consumer;

import com.example.paymentservice.dto.OrderCreatedDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RegionConsumer {
    @RabbitListener(queues = "order.india.queue")
    public void handleIndiaOrder(OrderCreatedDto event) {
        System.out.println("[INDIA] Processing Payment for Order: " + event.getOrderId());
        System.out.println("[INDIA] Amount :" + event.getAmount());
        System.out.println("[INDIA] Payment Successful");
    }

    @RabbitListener(queues = "order.us.queue")
    public void handleUsOrder(OrderCreatedDto event) {
        System.out.println("[US] Processing Payment for Order: " + event.getOrderId());
        System.out.println("[US] Amount :" + event.getAmount());
        System.out.println("[US] Payment Successful");
    }
}
