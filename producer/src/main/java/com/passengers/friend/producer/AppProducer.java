package com.passengers.friend.producer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.passengers.friend.data.config.JacksonConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.passengers.friend.producer.service",
        "com.passengers.friend.data.service",
        "com.passengers.friend.producer.listener"
})
@EnableJpaRepositories(basePackages = {"com.passengers.friend.data.repository" })
@EntityScan(basePackages = "com.passengers.friend.data.entity")
@EnableAutoConfiguration
@EnableScheduling
@ImportAutoConfiguration(JacksonConfig.class)
@EnableRabbit
public class AppProducer  {

    public static final String EXCHANGE_NAME = "myExchange";

    public static final String ROUTING_KEY = "my.key";

    @Value("${ff.rabbitmq.queue}")
    private String queue;

    @Bean
    public Queue appQueueGeneric() {
        return new Queue(queue);
    }

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding declareBindingGeneric() {
        return BindingBuilder.bind(appQueueGeneric()).to(appExchange()).with(ROUTING_KEY);
    }


    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    private ConnectionFactory rabbitConnectionFactory;

    @Autowired
    public void setConnectionFactory(ConnectionFactory rabbitConnectionFactory){
        this.rabbitConnectionFactory = rabbitConnectionFactory;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    private ObjectMapper mapper;

    @Autowired
    public void setObjectMapper(ObjectMapper mapper){
        this.mapper = mapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(AppProducer.class, args);
    }
}
