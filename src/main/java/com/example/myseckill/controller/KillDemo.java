package com.example.myseckill.controller;


import com.example.myseckill.dto.*;
import com.example.myseckill.rabbitmq.config.MessagingConfig;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/demo")
public class KillDemo
{
    @Autowired
    private AmqpTemplate template;

    @PostMapping("/kill")
    public String kill() {
        Random random = new Random();
//        order2.setOrderId(UUID.randomUUID().toString());
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGoods_id(1L);
//        orderInfo.setGoods_count(random.nextInt(3));
        orderInfo.setGoods_count(1);
        orderInfo.setId(random.nextLong());

        //OrderStatus orderStatus = new OrderStatus(order2, "PROCESS","order placed successfully in " + restaurantName );
        try {
            template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, orderInfo);
        } catch (AmqpException a) {
            return a.getMessage();
        }
        //receiveAndConvert
        return "Success !!";
    }
}
