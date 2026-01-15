package ru.rksp.samsonov.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "сырые_события_студентов")
public class RawEvent {
    @Id
    @Column(name = "идентификатор")
    private String идентификатор;

    @Column(name = "фамилия")
    private String фамилия;

    @Column(name = "имя")
    private String имя;

    @Column(name = "группа")
    private String группа;

    @Column(name = "дата_события")
    private LocalDate дата_события;

    @Column(name = "время_получения")
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
