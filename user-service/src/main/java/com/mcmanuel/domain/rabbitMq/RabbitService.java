package com.mcmanuel.domain.rabbitMq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitService {

    @RabbitListener(queuesToDeclare = @Queue("new-book-queue"))
    public void newBookListener(String message){
       log.info( message.isEmpty()? "nothing":message);
    }


    @RabbitListener(queuesToDeclare = @Queue("borrowed-book-queue"))
    public void borrowBookListener(String message){
       log.info( message.isEmpty()? "nothing":message);
    }

    @RabbitListener(queuesToDeclare = @Queue("returned-book-queue"))
    public void returnBookListener(String message){
       log.info( message.isEmpty()? "nothing":message);
    }


}
