package com.example.myseckill.taskexecutor;

import com.example.myseckill.taskexecutor.task.MergeOrderAndUpdateStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MergeTaskExcutor {
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void atStartup() {
        MergeOrderAndUpdateStock classeTaskRunn = applicationContext.getBean(MergeOrderAndUpdateStock.class);
        taskExecutor.execute(classeTaskRunn );
    }
}
