package ru.example.test.boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.test.boot.entities.Files;

public interface FilesRepository extends JpaRepository<Files, Long> {
}
