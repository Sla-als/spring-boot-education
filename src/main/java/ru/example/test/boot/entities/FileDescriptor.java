package ru.example.test.boot.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "files")
public class FileDescriptor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Нужно ли?

    @Column(name = "path")
    private String path;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "lesson_files",
            joinColumns = @JoinColumn(name = "file_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private List<Lesson> lessonsList;

    @CreationTimestamp
    @Column(name = "upload_data")
    private LocalDateTime upload_data;

    public FileDescriptor(String path, String name, Lesson lesson) {
        this.lessonsList = new ArrayList<>();
        this.path = path;
        this.name = name;
        lessonsList.add(lesson);

    }
}