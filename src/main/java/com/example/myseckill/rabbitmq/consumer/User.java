package com.example.myseckill.rabbitmq.consumer;

import com.example.myseckill.rabbitmq.config.MessagingConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class User {

    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void consumeMessageFromQueue(Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            String s = new String(message.getBody());
            System.out.println(s);
            Thread.sleep(2000);
            //channel.basicNack(deliveryTag, false, true);
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            //可以多次重复
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Message recieved from queue: " + orderStatus);
    }
}
