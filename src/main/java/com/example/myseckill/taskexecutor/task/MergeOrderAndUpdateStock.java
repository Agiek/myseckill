package com.example.myseckill.taskexecutor.task;

import com.example.myseckill.dto.DedectionPromise;
import com.example.myseckill.dto.Goods;
import com.example.myseckill.dto.OrderInfo;
import com.example.myseckill.concurrentcomponent.MergeQueue;
import com.example.myseckill.dto.Result;
import com.example.myseckill.mapper.GoodsMapper;
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

    @Autowired
    GoodsMapper goodsMapper;


    @Override
    public void run() {
        List<DedectionPromise> list = new ArrayList<>();
        while (true) {
            if(mergeQueue.isEmpty()) {
                try {
                    Thread.sleep(10);
                    continue;
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            int batchSize = mergeQueue.size();
            for(int i = 0; i < batchSize; i++) {
                list.add(mergeQueue.poll());
            }

            int sum = list.stream().mapToInt(e -> e.getDeductionRequest().getCount()).sum();
            long goodsId = list.get(0).getDeductionRequest().getGoodsId();
            int stock = goodsMapper.selectById(goodsId).getGoods_stock();
            if(sum <= stock) {
                goodsMapper.reduceStockById(goodsId, sum);
                for(DedectionPromise dedectionPromise : list) {
                    dedectionPromise.setResult(new Result(true, "ok"));
                    synchronized (dedectionPromise) {
                        dedectionPromise.notifyAll();
                    }
                }
                list.clear();
                continue;
            } else {
                for (DedectionPromise dedectionPromise : list) {
                    int count = dedectionPromise.getDeductionRequest().getCount();
                    if (count <= stock) {
                        stock -= count;
                        goodsMapper.reduceStockById(goodsId, count);
                        dedectionPromise.setResult(new Result(true, "ok"));
                    } else {
                        dedectionPromise.setResult(new Result(false, "库存不足"));
                    }
                    synchronized (dedectionPromise) {
                        dedectionPromise.notify();
                    }
                }
                list.clear();
                continue;
            }
        }
    }
}
