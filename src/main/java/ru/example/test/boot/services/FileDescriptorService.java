package ru.example.test.boot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.example.test.boot.dto.FileDescriptorDto;
import ru.example.test.boot.entities.FileDescriptor;
import ru.example.test.boot.entities.Lesson;
import ru.example.test.boot.exceptions.StorageException;
import ru.example.test.boot.exceptions.StorageFileNotFoundException;
import ru.example.test.boot.repositories.FileDescriptorRepository;
import ru.example.test.boot.services.interfaces.StorageService;
import ru.example.test.boot.storage.StorageProperties;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileDescriptorService implements StorageService {

    private final Path rootLocation;
    private final FileDescriptorRepository fileDescriptorRepository;
    private final StorageProperties properties;

    @Autowired
    public FileDescriptorService(StorageProperties properties, FileDescriptorRepository fileDescriptorRepository) {
        this.properties = properties;
        this.rootLocation = Paths.get(properties.getLocation());
        this.fileDescriptorRepository = fileDescriptorRepository;
    }

    @Override
    public void store(Lesson lesson, String filePath, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            if (!filePath.isEmpty()) {
                Files.createDirectory(rootLocation.resolve(filePath));
            }

            Files.copy(file.getInputStream(), this.rootLocation.resolve(filePath).resolve(file.getOriginalFilename()));
            FileDescriptor fileDescriptor = new FileDescriptor(rootLocation.resolve(filePath).toString(), file.getOriginalFilename(), lesson);
            fileDescriptorRepository.save(fileDescriptor);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public List<FileDescriptorDto> findAll() {
        return fileDescriptorRepository.findAll().stream().map(FileDescriptorDto::new).collect(Collectors.toList());
    }

    @Override
    public Optional<FileDescriptorDto> findFileById(Long id) {
        return fileDescriptorRepository.findById(id).map(FileDescriptorDto::new);
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
