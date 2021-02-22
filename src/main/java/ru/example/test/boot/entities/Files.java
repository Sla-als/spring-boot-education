package ru.example.test.boot.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "lesson_files")
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lesson_id;

    @Column(name = "file_id")
    private Long file_id;
}
