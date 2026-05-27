package com.mcmanuel.web;

import com.mcmanuel.configuration.ApplicationConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RabbitMQController {
    private final ApplicationConfiguration config;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping("send")
    public void sendMessage(@RequestBody String routingKey, @RequestBody String payload){
        rabbitTemplate.convertAndSend(config.ExchangeName(),routingKey,payload);
    }

}
