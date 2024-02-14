package ru.riji.stub.handlers;

import ru.riji.stub.service.TemplateHolder;

public class JsonHandler {
    public static String getMessage(String message){
        return  TemplateHolder.getTemplate("test2.json");
    }
}
