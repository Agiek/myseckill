package com.example.myseckill.controller;


import com.example.myseckill.concurrentcomponent.MergeCyclicCounter;
import com.example.myseckill.concurrentcomponent.MergeQueue;
import com.example.myseckill.dto.*;
import com.example.myseckill.mapper.OrderInfoMapper;
import com.example.myseckill.rabbitmq.config.MessagingConfig;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    MergeCyclicCounter mergeCyclicCounter;
    @Autowired
    MergeQueue mergeQueue;

    @PostMapping("/kill2")
    public String kill2() {
        Random random = new Random();
//        order2.setOrderId(UUID.randomUUID().toString());
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGoods_id(1L);
//        orderInfo.setGoods_count(random.nextInt(3));
        orderInfo.setGoods_count(1);
        orderInfo.setId(random.nextLong());

        //OrderStatus orderStatus = new OrderStatus(order2, "PROCESS","order placed successfully in " + restaurantName );
        orderInfo.setStatus(0);
        orderInfoMapper.insertOrderInfo(orderInfo);
        try {
            mergeCyclicCounter.await();
            Result result =  operate(new DeductionRequest(orderInfo.getId(), orderInfo.getGoods_id(),orderInfo.getGoods_count()));
        }catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        //receiveAndConvert
        return "Success !!";
    }
    private Result operate(DeductionRequest deductionRequest) throws InterruptedException {
        DedectionPromise dedectionPromise = new DedectionPromise(deductionRequest);
        synchronized (dedectionPromise) {
            boolean enqueueSuccess = mergeQueue.offer(dedectionPromise, 100, TimeUnit.MILLISECONDS);
            if (! enqueueSuccess) {
                return new Result(false, "系统繁忙");
            }
            try {
                dedectionPromise.wait(200);
                if (dedectionPromise.getResult() == null) {
                    return new Result(false, "等待超时");
                }
            } catch (InterruptedException e) {
                return new Result(false, "被中断");
            }

        }
        return dedectionPromise.getResult();
    }
}
