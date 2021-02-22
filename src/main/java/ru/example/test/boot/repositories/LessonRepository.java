package ru.example.test.boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.test.boot.entities.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
