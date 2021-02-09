package com.example.spring.producer.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerRabbitConfiguration {

    @Value("${spring.rabbitmq.request.routing-key.producer}")
    private String routingKey;

    @Value("${spring.rabbitmq.request.exchange.producer}")
    private String exchange;

    @Value("${spring.rabbitmq.request.dead-letter.producer}")
    private String deadLetter;

    @Value("${spring.rabbitmq.request.parking-lot.producer}")
    private String parkingLot;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(routingKey)
                .deadLetterExchange(exchange)
                .deadLetterRoutingKey(deadLetter)
                .build();
    }

    @Bean
    public Queue deadLetter() {
        return QueueBuilder.durable(deadLetter)
                .deadLetterExchange(exchange)
                .deadLetterRoutingKey(routingKey)
                .build();
    }

    @Bean
    public Queue parkingLot() {
        return new Queue(parkingLot);
    }

    @Bean
    public Binding bindingQueue() {
        return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
    }

    @Bean
    public Binding bindingDeadLetter() {
        return BindingBuilder.bind(deadLetter()).to(exchange()).with(deadLetter);
    }

    @Bean
    public Binding bindingParkingLot() {
        return BindingBuilder.bind(parkingLot()).to(exchange()).with(parkingLot);
    }
}