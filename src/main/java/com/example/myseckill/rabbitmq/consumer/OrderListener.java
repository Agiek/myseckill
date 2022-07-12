package com.example.myseckill.rabbitmq.consumer;

import com.example.myseckill.concurrentcomponent.MergeCyclicCounter;
import com.example.myseckill.dto.DeductionRequest;
import com.example.myseckill.dto.OrderInfo;
import com.example.myseckill.concurrentcomponent.MergeQueue;
import com.example.myseckill.dto.DedectionPromise;
import com.example.myseckill.dto.Result;
import com.example.myseckill.mapper.OrderInfoMapper;
import com.example.myseckill.rabbitmq.config.MessagingConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class OrderListener {
    @Autowired
    MergeQueue mergeQueue;

    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    MergeCyclicCounter mergeCyclicCounter;
    //private static BlockingQueue<RequestPromise> queue = new LinkedBlockingQueue<>(10);
    //可配置多线程
    //阿里建议不使用Executors去创建线程池，因为这个隐藏了线程池的实现
    //
    //使用ThreadPoolExecutor去创建，更加明确线程池的运行规则，规避资源耗尽的风险。
    @RabbitListener(queues = MessagingConfig.QUEUE,containerFactory = "customContainerFactory")
    public void OrderListener(OrderInfo orderInfo) throws InterruptedException {
//        boolean enqueueSuccess = mergeQueue.offer(orderInfo, 200, TimeUnit.MILLISECONDS);
        orderInfo.setStatus(0);
        orderInfoMapper.insertOrderInfo(orderInfo);
        try {
            mergeCyclicCounter.await();
            Result result =  operate(new DeductionRequest(orderInfo.getId(), orderInfo.getGoods_id(),orderInfo.getGoods_count()));
        }catch (InterruptedException ie) {
            ie.printStackTrace();
        }

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
