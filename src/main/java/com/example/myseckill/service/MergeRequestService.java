package com.example.myseckill.service;

import com.example.myseckill.dto.DedectionPromise;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class MergeRequestService {

    BlockingQueue<DedectionPromise> queue = new LinkedBlockingQueue<>();



}
