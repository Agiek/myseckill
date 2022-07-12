package com.example.myseckill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeductionRequest {
    private Long orderId;
    private Long goodsId;
    private Integer count;

}
