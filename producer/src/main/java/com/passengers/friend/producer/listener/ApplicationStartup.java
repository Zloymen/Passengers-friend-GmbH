package com.passengers.friend.producer.listener;

import com.passengers.friend.data.service.DebriefingService;
import com.passengers.friend.producer.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final ProducerService producerService;

    private final DebriefingService debriefingService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("run app");
        debriefingService.updateTask();
        producerService.getCheckRun().set(true);

    }
}
