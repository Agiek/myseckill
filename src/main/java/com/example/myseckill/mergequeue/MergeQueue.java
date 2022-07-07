package com.example.myseckill.mergequeue;

import com.example.myseckill.dto.OrderInfo;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;

@Component
public class MergeQueue extends LinkedBlockingDeque<OrderInfo> {
    public MergeQueue() {
        new LinkedBlockingDeque<OrderInfo>(10);
    }

}
