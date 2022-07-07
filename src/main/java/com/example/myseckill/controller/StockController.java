package com.example.myseckill.controller;

import com.example.myseckill.dto.Goods;
import com.example.myseckill.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private GoodsMapper goodsMapper;
    @GetMapping
    @ResponseBody
    public List<Goods> goodsList() {
        return goodsMapper.selectAll();
    }


}
