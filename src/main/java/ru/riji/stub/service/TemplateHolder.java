package ru.riji.stub.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URL;

@Service
public class TemplateHolder {

    private static final Map<String, String> templates = new HashMap<>();

    public static String getTemplate(String key){
        return templates.get(key);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void readTemplates(){
        try {
            templates.put("test1.xml", readFile("test1.xml"));
            templates.put("test2.json", readFile("test2.json"));
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private String readFile(String fileName) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("responses/" + fileName);
        byte[] binaryData = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
        return new String(binaryData, StandardCharsets.UTF_8);
    }

}
