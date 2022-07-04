package com.example.myseckill.mapper;

import com.example.myseckill.dto.Goods;

import java.util.List;

public interface GoodsMapper {

    int insertGoods(Goods goods);
    Goods selectById(long id);
    List<Goods> selectAll();

}
