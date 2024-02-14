package ru.riji.stub.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DelayTaskDto {
    private long id;
    private String service;
    private long delay;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;

    public DelayTaskDto(long id, String service, long delay, LocalDateTime startAt) {
        this.id = id;
        this.service = service;
        this.delay = delay;
        this.startAt = startAt;
    }
}
