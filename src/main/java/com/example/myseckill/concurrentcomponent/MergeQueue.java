package com.example.myseckill.concurrentcomponent;

import com.example.myseckill.dto.DedectionPromise;
import com.example.myseckill.dto.OrderInfo;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;

@Component
public class MergeQueue extends LinkedBlockingDeque<DedectionPromise> {
    public MergeQueue() {
//        new LinkedBlockingDeque<OrderInfo>(10);
        super(10);
    }

}
