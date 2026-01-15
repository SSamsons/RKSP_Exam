package ru.rksp.samsonov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rksp.samsonov.dto.EventDto;
import ru.rksp.samsonov.entity.RawEvent;
import ru.rksp.samsonov.repository.RawEventRepository;

@Service
public class EventProcessorService {

    @Autowired
    private RawEventRepository rawEventRepository;

    @Transactional
    public void processEvent(EventDto eventDto) {
        RawEvent rawEvent = new RawEvent();
        rawEvent.setИдентификатор(eventDto.getИдентификатор());
        rawEvent.setФамилия(eventDto.getФамилия());
        rawEvent.setИмя(eventDto.getИмя());
        rawEvent.setГруппа(eventDto.getГруппа());
        rawEvent.setДата_события(eventDto.getДата_события());
        rawEvent.setВремя_получения(eventDto.getВремя_получения());
        rawEventRepository.save(rawEvent);
    }
}
