package com.example.myseckill.mapper;

import com.example.myseckill.dto.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMapper {

    int insertGoods(Goods goods);
    Goods selectById(long id);
    List<Goods> selectAll();
    int reduceStockById(@Param("id")long id, @Param("count")int count);

}
