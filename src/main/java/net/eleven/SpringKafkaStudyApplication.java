package net.eleven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

/**
 * Created by eleven on 04.05.2019.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = KafkaAutoConfiguration.class)
public class SpringKafkaStudyApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaStudyApplication.class, args);
    }
}
