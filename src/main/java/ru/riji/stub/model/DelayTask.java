package ru.riji.stub.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.riji.stub.service.DelayService;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

@Data
public class DelayTask implements Runnable {
    private static long counter;

    private long id;
    private String service;
    private long delay;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;
    private DelayService delayService;
    private ScheduledFuture<?> scheduledFuture;

    public DelayTask(String service, long delay, LocalDateTime startAt, DelayService delayService) {
        this.id = ++counter;
        this.service = service;
        this.delay = delay;
        this.startAt = startAt;
        this.delayService = delayService;
    }

    @Override
    public void run() {
        System.out.println("run");
        delayService.setDelay(service,delay);
    }
}
