package ru.example.test.boot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import ru.example.test.boot.dto.LessonDto;
import ru.example.test.boot.entities.Lesson;
import ru.example.test.boot.repositories.FilesRepository;
import ru.example.test.boot.repositories.LessonRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final FilesRepository filesRepository;

    public List<LessonDto> findAll() {
        return lessonRepository.findAll().stream().map(LessonDto::new).collect(Collectors.toList());
    }

    public Lesson saveOrUpdate(Lesson product) {
        return lessonRepository.save(product);
    }


    public Optional<LessonDto> findDTOById(Long id) {
        return lessonRepository.findById(id).map(LessonDto::new);
    }

    public Optional<Lesson> findById(Long id) {
        return lessonRepository.findById(id);
    }


    @PutMapping
    public Lesson saveOrUpdate(Long id, Lesson newlesson) {
        return lessonRepository.findById(id)
                .map(lesson -> {
                    lesson.setTitle(newlesson.getTitle());
                    lesson.setDescription(newlesson.getDescription());
                    return lessonRepository.save(lesson);
                })
                .orElseGet(() -> {
                    newlesson.setId(id);
                    return lessonRepository.save(newlesson);
                });
    }
}
