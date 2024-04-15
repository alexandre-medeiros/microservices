package com.himax.ead.course.api.v1.consumers;

import com.himax.ead.course.api.v1.model.UserEventDto;
import com.himax.ead.course.domain.enums.ActionType;
import com.himax.ead.course.domain.model.Users;
import com.himax.ead.course.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.ExchangeTypes.FANOUT;

@RequiredArgsConstructor
@Component
public class UserEventConsumer {

    private final UserService service;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.userEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.userEventExchange}", type = FANOUT, ignoreDeclarationExceptions = "true"))
    )
    public void consumerUserEvent(@Payload UserEventDto dto) {
        Users user = dto.toModel();
        switch (ActionType.valueOf(dto.getActionType())) {
            case CREATE -> service.create(user);
            case UPDATE -> service.update(user);
            case DELETE -> service.delete(user);
        }
    }
}
