package ru.riji.stub.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.riji.stub.dto.DelayTaskDto;
import ru.riji.stub.exceptions.BreakRequestException;
import ru.riji.stub.form.DelayForm;
import ru.riji.stub.handlers.JsonHandler;
import ru.riji.stub.handlers.XmlHandler;
import ru.riji.stub.model.DelayTask;
import ru.riji.stub.model.ServiceModel;
import ru.riji.stub.service.DelayService;
import ru.riji.stub.service.SchedulerService;

import java.util.List;
import java.util.Map;

@RestController
public class ApiController {

    @Autowired
    private DelayService delayService;
    @Autowired
    private SchedulerService schedulerService;

    @GetMapping(value = {"/stub/**"})
    public ResponseEntity<?> httpGet(HttpServletRequest request){
        return doDelay(request.getRequestURI(), "", "");
    }

    @PostMapping(value = {"/stub/**"})
    public ResponseEntity<?> httpPost(HttpServletRequest request, @RequestBody String requestBody){
        String uri = request.getRequestURI();
        String response = "";
        return doDelay(uri, requestBody, response);
    }

    @PostMapping(value = {"/stub/json"})
    public ResponseEntity<?> stubJson(HttpServletRequest request, @RequestBody String requestBody){
        String uri = request.getRequestURI();
        String response = JsonHandler.getMessage(requestBody);
        return doDelay(uri, requestBody, response);
    }

    @PostMapping(value = {"/stub/xml"})
    public ResponseEntity<?> stubXml(HttpServletRequest request, @RequestBody String requestBody){
        String uri = request.getRequestURI();
        String response = XmlHandler.getMessage(requestBody);
        return doDelay(uri, requestBody, response);
    }

    public ResponseEntity<?> doDelay(String name, String request, String response){
        try {
            delayService.doDelay(name, request,response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BreakRequestException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*Stub services*/

    @GetMapping(value = {"/services"})
    public Map<String, ServiceModel> getServices(){
        return delayService.getServices();
    }

    @GetMapping(value = {"/tasks"})
    public List<DelayTaskDto> getTasks(){
        return schedulerService.getTasks().stream().map(x->new DelayTaskDto(x.getId(),x.getService(),x.getDelay(),x.getStartAt())).toList();
    }

    @PostMapping(value = {"/tasks/delete"})
    public void  deleteTask(@RequestParam("id") long id){
        schedulerService.deleteTask(id);
    }

    @PostMapping(value = {"/clearDelays"})
    public void  clearDelays(){
        delayService.clearDelays();
    }

    @PostMapping(value = {"/addDelayTask"})
    public List<DelayTaskDto> addTasks(DelayForm form){
        List<DelayTask> tasks = schedulerService.createTasks(form);
        return tasks.stream().map(x->new DelayTaskDto(x.getId(),x.getService(),x.getDelay(),x.getStartAt())).toList();
    }

    @PostMapping(value = {"/deleteAllTasks"})
    public void  deleteAllTasks(){
        schedulerService.deleteTasks();
    }


}
