package ru.example.test.boot.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.example.test.boot.dto.LessonDto;
import ru.example.test.boot.entities.Lesson;
import ru.example.test.boot.services.LessonService;
import ru.example.test.boot.services.FileDescriptorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final FileDescriptorService fileDescriptorService;

    @GetMapping
    public List<LessonDto> showAll() {
        return lessonService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<LessonDto> findById(@PathVariable Long id) {
        return lessonService.findDTOById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lesson saveNewLesson(@RequestBody Lesson lesson) {
        return lessonService.saveOrUpdate(lesson);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Lesson updateLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
        return lessonService.saveOrUpdate(id, lesson);
    }

}

