package com.passengers.friend.producer.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerService {

    @Getter
    private AtomicBoolean checkRun = new AtomicBoolean(false);

    private final RabbitService rabbitService;

    @Scheduled(fixedDelayString = "${ff.task.period}", initialDelay = 2000L)
    public void sendMessage() {
        if(!checkRun.get()) return;
        rabbitService.setTasksIntoMQ();
    }


}
