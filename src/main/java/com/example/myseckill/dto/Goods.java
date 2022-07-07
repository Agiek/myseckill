package com.example.myseckill.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Goods {
    private Long id;
    private String goods_name;
    private String goods_title;
    private String goods_img;
    private String goods_detail;
    private Double goods_price;
    private Integer goods_stock;

}