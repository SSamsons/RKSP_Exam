package ru.rksp.samsonov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rksp.samsonov.entity.RawEvent;

@Repository
public interface RawEventRepository extends JpaRepository<RawEvent, String> {
}
