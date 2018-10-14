package com.passengers.friend.producer.service;

import com.passengers.friend.data.entity.DebriefingTask;
import com.passengers.friend.data.entity.EWorkType;
import com.passengers.friend.data.repository.DebriefingTaskDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.passengers.friend.producer.AppProducer.EXCHANGE_NAME;
import static com.passengers.friend.producer.AppProducer.ROUTING_KEY;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitServiceImp implements RabbitService {

    @Value("${ff.rabbitmq.queue}")
    private String queue;

    @Value("${ff.task.count}")
    private Integer offset;

    private final RabbitTemplate rabbitTemplate;

    private final DebriefingTaskDao debriefingTaskDao;

    @Transactional
    public void runMessage(){
        for(int i = 1; i < 100; i++) {

            rabbitTemplate.convertAndSend(queue, new DebriefingTask(LocalDate.now(), "test", "test"));
        }
        log.info("sendMessage");
    }

    @Transactional
    @Override
    public void setTasksIntoMQ(){
        Pageable page = PageRequest.of(0, offset);
        Page<DebriefingTask> taskPage =  debriefingTaskDao.getAndLockIsNew(EWorkType.NEW,page);

        for(DebriefingTask task : taskPage){
            task.setType(EWorkType.MQ);
            debriefingTaskDao.save(task);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, task);
        }
    }
}
