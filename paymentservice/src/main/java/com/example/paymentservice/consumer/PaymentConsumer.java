package com.example.paymentservice.consumer;

import com.example.paymentservice.dto.OrderCreatedDto;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component

public class PaymentConsumer {
//    @RabbitListener(queues = "payment.queue")
//    public void processPayment(OrderCreatedDto event) {
//        System.out.println("[DIRECT] Processing Payment for Order: " + event.getOrderId());
//        System.out.println("[DIRECT] Amount :" + event.getAmount());
//        System.out.println("[DIRECT] Payment Successful");
//    }

    @RabbitListener(queues = "notification.queue")
    public void notification(OrderCreatedDto event) {
        System.out.println(" Notification: " + event);
    }

    @RabbitListener(queues = "analytics.queue")
    public void analytics(OrderCreatedDto event) {
        System.out.println(" Analytics: " + event);
    }

    @RabbitListener(queues = "payment.queue")
    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public void processPayment(OrderCreatedDto event) {

        System.out.println("[DIRECT] Payment for Order: " + event.getOrderId());
        System.out.println("[DIRECT] Amount: " + event.getAmount());

        if (event.getAmount() == -1) {
            System.out.println("[DIRECT] Payment failed for Order: "
                    + event.getOrderId() + " - retrying..");

            throw new RuntimeException(
                    "Payment failed - invalid amount for order: " + event.getOrderId()
            );
        }

//        System.out.println("[DIRECT] Payment Successful");
    }
    @Recover
    public void recover(Exception e, OrderCreatedDto event) {
        System.out.println("==================================");
        System.out.println("[RECOVERY] all 2 attempts failed for order"+ event.getOrderId());
        System.out.println("[RECOVERY] Reason: " + e.getMessage());
        System.out.println("[RECOVERY] forwarding to DLQ...: " + e.getMessage());
        System.out.println("==================================");
        throw new AmqpRejectAndDontRequeueException(e);
    }
}


