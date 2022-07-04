package com.example.myseckill.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order2 {
    private String orderId;
    private long goodId;
    private int qty;
    private double price;
}
