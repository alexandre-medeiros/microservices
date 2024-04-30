package com.himax.ead.course.api.v1.publishers;

import com.himax.ead.course.api.v1.model.NotificationCommandDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationCommandPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${ead.broker.exchange.notificationCommandExchange}")
    private String notificationCommandExchange;

    @Value("${ead.broker.key.notificationCommandKey}")
    private String notificationCommandKey;

    public void publishNotificationCommand(NotificationCommandDto dto) {
        rabbitTemplate.convertAndSend(notificationCommandExchange, notificationCommandKey, dto);
    }
}
