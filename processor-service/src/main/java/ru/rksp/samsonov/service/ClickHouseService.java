package ru.rksp.samsonov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class ClickHouseService {

    @Value("${clickhouse.url}")
    private String clickhouseUrl;

    @Value("${clickhouse.username:default}")
    private String clickhouseUsername;

    @Value("${clickhouse.password:}")
    private String clickhousePassword;

    public void saveCount(long count) {
        try (Connection connection = DriverManager.getConnection(clickhouseUrl, clickhouseUsername, clickhousePassword)) {
            String sql = "INSERT INTO агрегаты_событий_студентов (дата_и_время_записи, количество_записей) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                statement.setLong(2, count);
                statement.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при записи в ClickHouse", e);
        }
    }
}
