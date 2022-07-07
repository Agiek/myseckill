package com.example.myseckill;

import com.example.myseckill.dto.OrderInfo;
import com.example.myseckill.rabbitmq.config.MessagingConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Random;

@SpringBootTest
public class RabbitmqTest {
//    @Autowired
//    private RabbitTemplate template;
    @Autowired
    AmqpTemplate amqpTemplate;

//

//    @Test
//    @DisplayName("测试发送消息")
//    public void testSend() {
//        Random random = new Random();
////        order2.setOrderId(UUID.randomUUID().toString());
//        OrderInfo orderInfo = new OrderInfo();
//        orderInfo.setGoods_id(1L);
//        orderInfo.setGoods_count(random.nextInt(3));
//        orderInfo.setId(random.nextLong());
//        //OrderStatus orderStatus = new OrderStatus(order2, "PROCESS","order placed successfully in " + restaurantName );
//        amqpTemplate.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, orderInfo);
//    }
//
//    @Test
//    @DisplayName("测试接收消息")
//    public void testReceive() {
//
//
//        Random random = new Random();
////        order2.setOrderId(UUID.randomUUID().toString());
//        OrderInfo orderInfo = new OrderInfo();
//        orderInfo.setGoods_id(1L);
//        orderInfo.setGoods_count(random.nextInt(3));
//        orderInfo.setId(random.nextLong());
//        //OrderStatus orderStatus = new OrderStatus(order2, "PROCESS","order placed successfully in " + restaurantName );
//        amqpTemplate.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, orderInfo);
//        OrderInfo orderInfo1 = (OrderInfo) amqpTemplate.receiveAndConvert(MessagingConfig.QUEUE);
//        Assert.assertEquals(orderInfo, orderInfo1);
//    }

}