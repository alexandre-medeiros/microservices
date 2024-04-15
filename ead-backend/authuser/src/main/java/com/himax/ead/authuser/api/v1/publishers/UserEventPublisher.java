package com.himax.ead.authuser.api.v1.publishers;

import com.himax.ead.authuser.api.v1.model.user.UserEventDto;
import com.himax.ead.authuser.domain.enums.ActionType;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    @Value("${ead.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    public void publishUserEvent(UserEventDto dto, ActionType actionType) {
        dto.setActionType(actionType.toString());
        rabbitTemplate.convertAndSend(exchangeUserEvent, "", dto);
    }
}
