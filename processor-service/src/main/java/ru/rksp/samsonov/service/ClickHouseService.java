package ru.rksp.samsonov.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ClickHouseService {

    private static final Logger logger = LoggerFactory.getLogger(ClickHouseService.class);

    @Value("${clickhouse.url}")
    private String clickhouseUrl;

    @Value("${clickhouse.username:default}")
    private String clickhouseUsername;

    @Value("${clickhouse.password:}")
    private String clickhousePassword;

    public void saveCount(long count) {
        try (Connection connection = DriverManager.getConnection(clickhouseUrl, clickhouseUsername, clickhousePassword)) {
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String sql = String.format(
                "INSERT INTO агрегаты_событий_студентов (дата_и_время_записи, количество_записей) VALUES ('%s', %d)",
                dateTime, count
            );
            
            logger.info("Выполнение запроса в ClickHouse: {}", sql);
            
            try (Statement statement = connection.createStatement()) {
                int rowsAffected = statement.executeUpdate(sql);
                logger.info("Успешно записано в ClickHouse: {} строк", rowsAffected);
            }
        } catch (Exception e) {
            logger.error("Ошибка при записи в ClickHouse: {}", e.getMessage(), e);
            throw new RuntimeException("Ошибка при записи в ClickHouse: " + e.getMessage(), e);
        }
    }
}
