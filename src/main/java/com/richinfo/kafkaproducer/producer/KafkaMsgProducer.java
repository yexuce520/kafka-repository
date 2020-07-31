package com.richinfo.kafkaproducer.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@EnableScheduling
public class KafkaMsgProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaMsgProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.app.topic.foo}")
    private String topic;

    public void sendMsg() {
        String message = "Hello World---" + System.currentTimeMillis();
        logger.info("topic="+topic+",message="+message);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.addCallback(success -> logger.info("KafkaMessageProducer 发送消息成功！"),
                fail -> logger.error("KafkaMessageProducer 发送消息失败！"));
    }
}
