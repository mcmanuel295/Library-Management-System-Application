package com.mcmanuel.domain;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @RabbitListener(queues = "${catalog.borrowBookQueue}" )
    public void borrowQueue(String messgae){
        System.out.println("new borrow request: "+messgae);
    }
}