package com.example.myseckill.taskexecutor.task;

import com.example.myseckill.dto.OrderInfo;
import com.example.myseckill.mergequeue.MergeQueue;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("application")
public class MergeOrderAndUpdateStock implements Runnable{

    @Autowired
    MergeQueue mergeQueue;

    @Override
    public void run() {
        List<OrderInfo> list = new ArrayList<>();
        while (true) {
            int batchSize = mergeQueue.size();
            for(int i = 0; i < list; i++) {

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("MergeOrderAndUpdateStock run ");
        }
    }
}
