package com.example.myseckill.rabbitmq.publisher;

import com.example.myseckill.rabbitmq.config.MessagingConfig;
import com.example.myseckill.dto.Order2;
import com.example.myseckill.dto.OrderStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderPublisher {

    @Autowired
    private RabbitTemplate template;

    @PostMapping("/{restaurantName}")
    public String bookOrder(@RequestBody Order2 order2, @PathVariable String restaurantName) {
        order2.setOrderId(UUID.randomUUID().toString());
        //restaurant service
        //payment service
        OrderStatus orderStatus = new OrderStatus(order2, "PROCESS","order placed successfully in " + restaurantName );
        template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, orderStatus);
        return "Success !!";
    }
}
