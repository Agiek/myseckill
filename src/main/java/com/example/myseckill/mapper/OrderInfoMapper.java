package com.example.myseckill.mapper;

import com.example.myseckill.dto.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderInfoMapper {
    int insertOrderInfo(OrderInfo orderInfo);
    OrderInfo selectById(long id);
    List<OrderInfo> selectAll();


}
