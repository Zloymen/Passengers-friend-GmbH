package com.passengers.friend.executor.listener;

import com.passengers.friend.data.entity.DebriefingTask;
import com.passengers.friend.executor.service.ExecutorService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExecutorListener {

    private final ExecutorService executorService;

    @RabbitListener(queues = "${ff.rabbitmq.queue}")
    public void receiveMessage(final DebriefingTask task, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        log.info("Received message as generic: {}, tag: {}", task, tag);
        try {
            executorService.transform(task);
            channel.basicAck(tag, false);
        }catch (Exception e){
            log.error(String.format("error task day: %s, code: %s, number: %s", task.getDay(), task.getIcao(), task.getNumber()) , e);
            throw e;
        }

    }
}
