package ru.example.test.boot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.example.test.boot.entities.FileDescriptor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class FileDescriptorDto {
    private Long id;
    private String path;
    private String name;
    private LocalDateTime upload_data;

    public FileDescriptorDto(FileDescriptor fileDescriptor) {
        this.id = fileDescriptor.getId();
        this.path = fileDescriptor.getPath();
        this.name = fileDescriptor.getName();
        this.upload_data = fileDescriptor.getUpload_data();
    }
}
