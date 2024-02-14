package ru.riji.stub.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.jfr.DataAmount;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DelayForm {
    private String[] services;
    private long startDelay;
    private long stopDelay;
    private long step;
    private long time;
    private LocalDateTime startAt;
}
