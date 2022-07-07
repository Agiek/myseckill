package com.example.myseckill.rabbitmq.consumer;

import com.example.myseckill.dto.OrderInfo;
import com.example.myseckill.mergequeue.MergeQueue;
import com.example.myseckill.rabbitmq.config.MessagingConfig;
import com.rabbitmq.client.Channel;
import lombok.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class OrderListener {
    @Autowired
    MergeQueue mergeQueue;
    //private static BlockingQueue<RequestPromise> queue = new LinkedBlockingQueue<>(10);
    //可配置多线程
    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void OrderListener(OrderInfo orderInfo) throws InterruptedException {
        boolean enqueueSuccess = mergeQueue.offer(orderInfo, 200, TimeUnit.MILLISECONDS);
        if(! enqueueSuccess) {
            //写入失败订单，mergeQueue繁忙
            //new LinkedBlockingDeque(1);
        }
    }
}
@Data
class RequestPromise{
    private OrderInfo orderInfo;
    private Result result;
}
@Data
class Result {
    private Boolean success;
    private String msg;
}
