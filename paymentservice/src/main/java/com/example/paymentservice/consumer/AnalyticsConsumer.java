package com.example.paymentservice.consumer;

import com.example.paymentservice.dto.OrderCreatedDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsConsumer {

    @RabbitListener(queues = "analytics.queue")
    public void handleAnalytics(OrderCreatedDto event) {
        System.out.println("[FANOUT-ANALYTICS] Order: " + event.getOrderId());
        System.out.println("[FANOUT-ANALYTICS] Storing analytics data...");
    }
}