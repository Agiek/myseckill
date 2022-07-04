package com.example.myseckill.mapper;

import com.example.myseckill.dto.OrderInfo;

import java.util.List;

public interface OrderInfoMapper {
    int insertOrderInfo(OrderInfo orderInfo);
    OrderInfo selectById(long id);
    List<OrderInfo> selectAll();


}
