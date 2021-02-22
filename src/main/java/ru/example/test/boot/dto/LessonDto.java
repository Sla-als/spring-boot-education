package ru.example.test.boot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.example.test.boot.entities.FileDescriptor;
import ru.example.test.boot.entities.Lesson;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class LessonDto {
    private Long id;
    private String title;
    private String description;
    private List<Long> idFilesList;

    public LessonDto(Lesson lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.description = lesson.getDescription();
        this.idFilesList = lesson.getFilesList().stream().map(FileDescriptor::getId).collect(Collectors.toList());
    }
}
