package com.example.paymentservice.consumer;

import com.example.paymentservice.dto.OrderCreatedDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer{

    @RabbitListener(queues = "notification.queue")
    public void handleNotification(OrderCreatedDto event) {
        System.out.println("[FANOUT-NOTIFICATION] Order: " + event.getOrderId());
        System.out.println("[FANOUT-NOTIFICATION] Sending Email/SMS...");
    }
    public void add(){
        System.out.println("[FANOUT-NOTIFICATION] Adding Order...");
    }
}
