package com.mcmanuel.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
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
        return QueueBuilder.durable(appConfig.borrowBookQueue()).build() ;
    }

    @Bean
    Binding borrowBookQueueBinding(){
        return BindingBuilder
                .bind(borrowBookQueue())
                .to(exchange())
                .with(appConfig.borrowBookQueue());
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


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonConverter());
        return rabbitTemplate;
    }

    @Bean
    public JacksonJsonMessageConverter jacksonConverter(){
        return new JacksonJsonMessageConverter();
    }
}
