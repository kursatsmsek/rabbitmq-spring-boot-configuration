package com.devkursat.rabbitmqexample.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DummyService {
    private final DirectExchange directExchange;

    private final AmqpTemplate rabbitTemplate;

    @Value("${sr.rabbit.routing.name}")
    private String routingName;

    @Value("${sr.rabbit.exchange.name}")
    private String exchangeName;

    public DummyService(DirectExchange directExchange, AmqpTemplate rabbitTemplate) {
        this.directExchange = directExchange;
        this.rabbitTemplate = rabbitTemplate;
    }
    public void requestReceiverService() {
        List<Integer> list = new ArrayList<>();
        rabbitTemplate.convertAndSend(directExchange.getName(), routingName, list);
    }

    @RabbitListener(queues = "${sr.rabbit.queue.name}")
    public void transferMoneyMessage(ArrayList list) throws InterruptedException {
        for (int i = 1; i <= 100; i++) {
            list.add(i);
        }
        Thread.sleep(3000);

        System.out.println("List successfully filled.");
        System.out.println("List length: " + list.size());
    }

}
