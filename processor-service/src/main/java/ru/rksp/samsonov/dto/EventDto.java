package ru.rksp.samsonov.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventDto {
    private String идентификатор;
    private String фамилия;
    private String имя;
    private String группа;
    private LocalDate дата_события;
    private LocalDateTime время_получения;

    public String getИдентификатор() {
        return идентификатор;
    }

    public void setИдентификатор(String идентификатор) {
        this.идентификатор = идентификатор;
    }

    public String getФамилия() {
        return фамилия;
    }

    public void setФамилия(String фамилия) {
        this.фамилия = фамилия;
    }

    public String getИмя() {
        return имя;
    }

    public void setИмя(String имя) {
        this.имя = имя;
    }

    public String getГруппа() {
        return группа;
    }

    public void setГруппа(String группа) {
        this.группа = группа;
    }

    public LocalDate getДата_события() {
        return дата_события;
    }

    public void setДата_события(LocalDate дата_события) {
        this.дата_события = дата_события;
    }

    public LocalDateTime getВремя_получения() {
        return время_получения;
    }

    public void setВремя_получения(LocalDateTime время_получения) {
        this.время_получения = время_получения;
    }
}
