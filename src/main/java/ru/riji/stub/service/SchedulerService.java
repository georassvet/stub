package ru.riji.stub.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import ru.riji.stub.form.DelayForm;
import ru.riji.stub.model.DelayTask;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private ThreadPoolTaskScheduler scheduler;
    @Autowired
    private DelayService delayService;

    @Getter
    private List<DelayTask> tasks = new ArrayList<>();


    public List<DelayTask> createTasks(DelayForm form) {
        if (form.getStartDelay() < form.getStopDelay()) {
            asc(form);
        } else {
            desc(form);
        }
        return tasks;
    }

    private void asc(DelayForm form) {
        for(String service : form.getServices()) {
            long startDelay = form.getStartDelay();
            long stopDelay = form.getStopDelay();
            LocalDateTime startTime = form.getStartAt();
            while (startDelay <= stopDelay) {
                DelayTask task = new DelayTask(service, startDelay, startTime, delayService);
                ZonedDateTime zdt = startTime.atZone(ZoneId.systemDefault());
                Date output = Date.from(zdt.toInstant());
                System.out.println(output);
                task.setScheduledFuture(scheduler.schedule(task, output));

                tasks.add(task);
                startDelay = startDelay + form.getStep();
                startTime = startTime.plusMinutes(form.getTime());
            }
        }
    }

    private void desc(DelayForm form) {

    }

    public void deleteTask(long id) {
        DelayTask delayTask = tasks.stream().filter(x->x.getId()==id).findFirst().get();
        delayTask.getScheduledFuture().cancel(true);
        tasks.remove(delayTask);
    }

    public void deleteTasks() {
        for (DelayTask task : tasks) {
            task.getScheduledFuture().cancel(true);
        }
        tasks = new ArrayList<>();
    }
}
