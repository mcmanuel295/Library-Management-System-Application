package com.mcmanuel.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {
    private final ApplicationConfiguration appConfig;

    @Bean
    DirectExchange exchange(){
        return new DirectExchange(appConfig.ExchangeName());
    }

    @Bean
    Queue borrowBookQueue(){
        return QueueBuilder.durable(appConfig.borrowQueue()).build() ;
    }

    @Bean
    Binding borrowBookQueueBinding(){
        return BindingBuilder
                .bind(borrowBookQueue())
                .to(exchange())
                .with(appConfig.borrowQueue());
    }


    @Bean
    Queue returnBookQueue(){
        return QueueBuilder.durable(appConfig.returnQueue()).build();
    }


    @Bean
    Binding returnBookQueueBinding(){
        return BindingBuilder
                .bind(returnBookQueue())
                .to(exchange())
                .with(appConfig.returnQueue());
    }

}
