package com.passengers.friend.producer.service;

public interface RabbitService {

    void runMessage();
    void setTasksIntoMQ();
}
