package ru.riji.stub.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsController {

    @Autowired
    private JmsTemplate jmsTemplate;
}
