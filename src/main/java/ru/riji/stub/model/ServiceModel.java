package ru.riji.stub.model;

import lombok.Data;
import ru.riji.stub.service.DelayService;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.LongAdder;

@Data
public class ServiceModel {
    private String name;
    private String request;
    private String response;
    private Long currentDelay;
    private Long defaultDelay;
    private Long maxDelay;
    private final LongAdder counter = new LongAdder();
    private boolean needUpdate;
    private LocalDateTime timeAt;

    public ServiceModel(String name) {
        this.name = name;
        this.currentDelay = DelayService.defaultDelay;
        this.defaultDelay = DelayService.defaultDelay;
        this.maxDelay = DelayService.maxDelay;
        this.needUpdate = true;
    }

   public void increment(){
        counter.increment();
    }

    public void reset() {
        this.counter.reset();
        this.needUpdate = true;
    }
}
