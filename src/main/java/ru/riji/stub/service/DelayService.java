package ru.riji.stub.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.riji.stub.exceptions.BreakRequestException;
import ru.riji.stub.model.ServiceModel;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class DelayService {

    @Getter
    public  Map<String, ServiceModel> services = new HashMap<>();
    public static Long defaultDelay = 1000L;
    public static Long maxDelay = 1000L;

    @Autowired
    private InfluxExporter influxExporter;

    public void setServices(Map<String, ServiceModel> services) {
        this.services = services;
    }

    public void setDelay(String name, Long delay){
        ServiceModel serviceModel = getServiceAndCount(name);
        serviceModel.setCurrentDelay(delay);
    }

    public void doDelay(String name, String request, String response ) throws BreakRequestException {
        ServiceModel serviceModel = getServiceAndCount(name);
        if(serviceModel.isNeedUpdate()){
            serviceModel.setRequest(request);
            serviceModel.setResponse(response);
            serviceModel.setNeedUpdate(false);
            serviceModel.setTimeAt(LocalDateTime.now());
        }
        try {
            Thread.sleep(serviceModel.getCurrentDelay());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private ServiceModel getServiceAndCount(String name){
        ServiceModel serviceModel = services.get(name);
        if(serviceModel == null){
            serviceModel =  new ServiceModel(name);
            services.put(name, serviceModel);
        }
        serviceModel.increment();
        return serviceModel;
    }

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void sendMetrics() throws IOException {
        influxExporter.sendMetrics(services);
        resetCounter();
    }

    private void resetCounter(){
        services.forEach((key, value) -> value.reset());
    }

    public void clearDelays() {
        services = new HashMap<>();
    }
}
