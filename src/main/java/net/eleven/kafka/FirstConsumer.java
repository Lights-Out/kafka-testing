package net.eleven.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by eleven on 04.05.2019.
 */
@Service
public class FirstConsumer {

    @Autowired
    private CountDownLatch latch;

    public FirstConsumer() {
        System.out.println("consumer created");
    }

    @KafkaListener(topics = "messages", group = "test-group-id")
    public void listen(List<ConsumerRecord<String, String>> message) {
        latch.countDown();
        System.out.println("123 message " + message);
    }
}
