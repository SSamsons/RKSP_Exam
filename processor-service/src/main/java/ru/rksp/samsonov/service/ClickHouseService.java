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
        Connection connection = null;
        try {
            // Формируем URL с параметрами подключения
            String connectionUrl = clickhouseUrl;
            if (!connectionUrl.contains("?")) {
                connectionUrl += "?user=" + clickhouseUsername;
                if (clickhousePassword != null && !clickhousePassword.isEmpty()) {
                    connectionUrl += "&password=" + clickhousePassword;
                }
            }
            
            logger.info("Подключение к ClickHouse: {}", connectionUrl.replaceAll("password=[^&]*", "password=***"));
            
            // Подключаемся к ClickHouse
            connection = DriverManager.getConnection(connectionUrl);
            logger.info("Подключение к ClickHouse установлено успешно");
            
            // Формируем SQL запрос с правильным форматом даты для ClickHouse
            // ClickHouse DateTime принимает формат: 'YYYY-MM-DD HH:MM:SS'
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String sql = String.format(
                "INSERT INTO агрегаты_событий_студентов (дата_и_время_записи, количество_записей) VALUES ('%s', %d)",
                dateTime, count
            );
            
            logger.info("Выполнение запроса в ClickHouse: {}", sql);
            
            try (Statement statement = connection.createStatement()) {
                // executeUpdate возвращает количество затронутых строк
                // В ClickHouse это может быть 0 или 1 для INSERT
                int rowsAffected = statement.executeUpdate(sql);
                logger.info("Успешно записано в ClickHouse: {} строк затронуто", rowsAffected);
                
                // Проверяем, что запрос выполнен успешно
                if (rowsAffected >= 0) {
                    logger.info("Данные успешно сохранены в ClickHouse: дата={}, количество={}", dateTime, count);
                } else {
                    logger.warn("Неожиданный результат executeUpdate: {}", rowsAffected);
                }
            }
        } catch (Exception e) {
            logger.error("Ошибка при записи в ClickHouse", e);
            logger.error("URL: {}", clickhouseUrl);
            logger.error("Username: {}", clickhouseUsername);
            logger.error("Password: {}", clickhousePassword != null && !clickhousePassword.isEmpty() ? "***" : "(пустой)");
            logger.error("Сообщение об ошибке: {}", e.getMessage());
            if (e.getCause() != null) {
                logger.error("Причина ошибки: {}", e.getCause().getMessage());
            }
            throw new RuntimeException("Ошибка при записи в ClickHouse: " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    logger.debug("Соединение с ClickHouse закрыто");
                } catch (Exception e) {
                    logger.warn("Ошибка при закрытии соединения с ClickHouse: {}", e.getMessage());
                }
            }
        }
    }
}
