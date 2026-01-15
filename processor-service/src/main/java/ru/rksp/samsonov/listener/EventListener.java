package ru.rksp.samsonov.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rksp.samsonov.dto.EventDto;
import ru.rksp.samsonov.service.EventProcessorService;

@Component
public class EventListener {

    @Autowired
    private EventProcessorService eventProcessorService;

    @RabbitListener(queues = "events.raw")
    public void handleEvent(EventDto eventDto) {
        eventProcessorService.processEvent(eventDto);
    }
}
