package com.mcmanuel.domain.rabbitMq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitService {

    @RabbitListener(queues = "catalog.new-book-queue")
    public void newBookListener(String message){
       log.info( message.isEmpty()? "nothing":message);
    }


    @RabbitListener(queues = "catalog.borrow-book-queue")
    public void borrowBookListener(String message){
       log.info( message.isEmpty()? "nothing":message);
    }

    @RabbitListener(queues = "catalog.return-book-queue")
    public void returnBookListener(String message){
       log.info( message.isEmpty()? "nothing":message);
    }


}
