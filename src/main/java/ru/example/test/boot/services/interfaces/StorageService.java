package ru.example.test.boot.services.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.example.test.boot.dto.FileDescriptorDto;
import ru.example.test.boot.entities.Lesson;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(Lesson lesson, String filePath, MultipartFile file);

    Stream<Path> loadAll();

    List<FileDescriptorDto> findAll();

    Optional<FileDescriptorDto> findFileById(Long id);

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

}
