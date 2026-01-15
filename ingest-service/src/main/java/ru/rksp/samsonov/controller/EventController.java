package ru.rksp.samsonov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rksp.samsonov.dto.EventDto;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/events")
@Tag(name = "Events", description = "API для работы с событиями")
public class EventController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    @Operation(summary = "Отправить событие в очередь")
    public ResponseEntity<String> createEvent(@RequestBody EventDto eventDto) {
        if (eventDto.getВремя_получения() == null) {
            eventDto.setВремя_получения(LocalDateTime.now());
        }
        rabbitTemplate.convertAndSend("events.raw", eventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Событие отправлено в очередь");
    }
}
