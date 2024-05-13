package com.example.CourseManagement.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic enrollTopic() {
            return TopicBuilder.name("enroll-topic")
                .build();
    }

    @Bean
    public NewTopic dropTopic() {
            return TopicBuilder.name("drop-topic")
                .build();
    }
    @Bean
    public NewTopic NotificationTopic() {
            return TopicBuilder.name("notification-topic")
                .build();
    }
}
