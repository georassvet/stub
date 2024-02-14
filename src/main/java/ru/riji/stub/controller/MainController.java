package ru.riji.stub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.riji.stub.form.DelayForm;
import ru.riji.stub.service.DelayService;
import ru.riji.stub.service.SchedulerService;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private static long[] delays = {0, 500, 1000, 5000, 10000};
    private static long[] times = {0, 1, 5, 15};
    private static long[] steps = {100, 500, 1000, 5000};

    @Autowired
    private DelayService delayService;
    @Autowired
    private SchedulerService schedulerService;

    @GetMapping(value = {"/"})
    public String index(Model model){
        model.addAttribute("form", new DelayForm());
        model.addAttribute("delays", delays);
        model.addAttribute("times", times);
        model.addAttribute("steps", steps);
        model.addAttribute("services", delayService.getServices().keySet().stream().toList());
        return "index";
    }

    @PostMapping(value = {"/delay/add"})
    public String addDelay(DelayForm form){
        schedulerService.createTasks(form);
        return "redirect:/";
    }
}
