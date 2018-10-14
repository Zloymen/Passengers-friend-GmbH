package com.passengers.friend.executor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.passengers.friend.data.config.JacksonConfig;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
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
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@SpringBootApplication
@ComponentScan(basePackages = {"com.passengers.friend.data.service",
        "com.passengers.friend.executor.service",
        "com.passengers.friend.executor.listener"
})
@EnableJpaRepositories("com.passengers.friend.data.repository")
@EntityScan("com.passengers.friend.data.entity")
@ImportAutoConfiguration(JacksonConfig.class)
@EnableAutoConfiguration
public class AppExecutor implements RabbitListenerConfigurer {

    @Value("${ff.rabbitmq.queue}")
    private String queueName;

    @Value("${ff.rabbitmq.executor.min}")
    private Integer minExecutor;

    @Value("${ff.rabbitmq.executor.max}")
    private Integer maxExecutor;

    @Value("${ff.rabbitmq.executor.prefetch}")
    private Integer prefetchCount;

    public static final String EXCHANGE_NAME = "myExchange";

    public static final String ROUTING_KEY = "my.key";

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding declareBindingGeneric() {
        return BindingBuilder.bind(appQueueGeneric()).to(appExchange()).with(ROUTING_KEY);
    }

    @Bean
    public Queue appQueueGeneric() {
        return new Queue(queueName);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());

        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory myRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(rabbitConnectionFactory);
        factory.setMaxConcurrentConsumers(maxExecutor);
        factory.setConcurrentConsumers(minExecutor);
        factory.setMessageConverter(producerJackson2MessageConverter());
        factory.setPrefetchCount(prefetchCount);

        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        return factory;
    }

    private ObjectMapper mapper;

    private ConnectionFactory rabbitConnectionFactory;

    @Autowired
    public void setConnectionFactory(ConnectionFactory rabbitConnectionFactory){
        this.rabbitConnectionFactory = rabbitConnectionFactory;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
        registrar.setContainerFactory(myRabbitListenerContainerFactory());
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    public static void main(String[] args) {
        SpringApplication.run(AppExecutor.class, args);
    }
}

