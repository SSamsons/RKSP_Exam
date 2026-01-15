CREATE TABLE IF NOT EXISTS сырые_события_студентов (
    идентификатор VARCHAR(255) PRIMARY KEY,
    фамилия VARCHAR(255),
    имя VARCHAR(255),
    группа VARCHAR(255),
    дата_события DATE,
    время_получения TIMESTAMP
);
