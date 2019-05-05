package net.eleven.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

/**
 * Created by eleven on 04.05.2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
public class FirstConsumerTest {

    private static final String TOPIC_NAME = "messages";

    private KafkaTemplate<String, String> producer;

    @Autowired
    private CountDownLatch latch;

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(2, true, 2, TOPIC_NAME);

    @Before
    public void setUp() {
        this.producer = buildKafkaTemplate();
        this.producer.setDefaultTopic(TOPIC_NAME);
    }

    private KafkaTemplate<String, String> buildKafkaTemplate() {
        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);

        ProducerFactory<String, String> pf = new DefaultKafkaProducerFactory<>(senderProps, new StringSerializer(), new StringSerializer());
        return new KafkaTemplate<>(pf);
    }

    @Test
    public void listenerShouldConsumeMessages() throws InterruptedException {
        producer.sendDefault("test-key", "Hello world");
        Assertions.assertThat(latch.await(10L, TimeUnit.SECONDS)).isTrue();

    }
}