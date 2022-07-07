package com.example.myseckill.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderInfo {
    private Long id;
    private Long user_id;
    private Long goods_id;
    private Long delivery_addr_id;
    private String goods_name;
    private Integer goods_count;
    private Double goods_price;
    private Integer order_channel;
    private Integer status;
    private Date create_date;
    private Date pay_date;

}
