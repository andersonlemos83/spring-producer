package com.example.spring.producer.service.impl;

import com.example.spring.producer.amqp.AmqpProducer;
import com.example.spring.producer.dto.MessageQueue;
import com.example.spring.producer.service.AmqpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQServiceImpl implements AmqpService {

    @Autowired
    private AmqpProducer<MessageQueue> amqpProducer;

    @Override
    public void sendToConsumer(MessageQueue messageQueue) {
        amqpProducer.producer(messageQueue);
    }
}