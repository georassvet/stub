package ru.riji.stub.handlers;

import ru.riji.stub.service.TemplateHolder;

public class XmlHandler {
    public static String getMessage(String message){
        return  TemplateHolder.getTemplate("test1.xml");
    }
}
