package ru.rksp.samsonov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rksp.samsonov.repository.RawEventRepository;
import ru.rksp.samsonov.service.ClickHouseService;

@RestController
@RequestMapping("/api/v1/events")
@Tag(name = "Events", description = "API для работы с событиями")
public class EventCountController {

    private static final Logger logger = LoggerFactory.getLogger(EventCountController.class);

    @Autowired
    private RawEventRepository rawEventRepository;

    @Autowired
    private ClickHouseService clickHouseService;

    @PostMapping("/count")
    @Operation(summary = "Получить количество записей и сохранить в ClickHouse")
    public ResponseEntity<String> getCount() {
        try {
            long count = rawEventRepository.count();
            logger.info("Получено количество записей из PostgreSQL: {}", count);
            clickHouseService.saveCount(count);
            return ResponseEntity.status(HttpStatus.OK).body("Количество записей: " + count + ". Данные сохранены в ClickHouse.");
        } catch (Exception e) {
            logger.error("Ошибка при получении статистики: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка: " + e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error("Необработанная ошибка: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ошибка сервера: " + e.getMessage());
    }
}
