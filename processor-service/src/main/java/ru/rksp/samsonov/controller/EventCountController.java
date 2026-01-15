package ru.rksp.samsonov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rksp.samsonov.repository.RawEventRepository;
import ru.rksp.samsonov.service.ClickHouseService;

@RestController
@RequestMapping("/api/v1/events")
@Tag(name = "Events", description = "API для работы с событиями")
public class EventCountController {

    @Autowired
    private RawEventRepository rawEventRepository;

    @Autowired
    private ClickHouseService clickHouseService;

    @PostMapping("/count")
    @Operation(summary = "Получить количество записей и сохранить в ClickHouse")
    public ResponseEntity<String> getCount() {
        long count = rawEventRepository.count();
        clickHouseService.saveCount(count);
        return ResponseEntity.status(HttpStatus.OK).body("Количество записей: " + count);
    }
}
