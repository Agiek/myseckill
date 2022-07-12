package com.example.myseckill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private Long id;
    private Long user_id;
    private Long order_id;
    private Long goods_id;
}
