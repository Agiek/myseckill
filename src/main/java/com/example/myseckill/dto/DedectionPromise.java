package com.example.myseckill.dto;

import lombok.Data;

@Data
public class DedectionPromise {
    private DeductionRequest deductionRequest;
    private Result result;
    public DedectionPromise(DeductionRequest deductionRequest) {
        this.deductionRequest = deductionRequest;
    }
}