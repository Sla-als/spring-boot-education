package ru.example.test.boot.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.example.test.boot.dto.FileDescriptorDto;
import ru.example.test.boot.entities.Lesson;
import ru.example.test.boot.exceptions.ResourceNotFoundException;
import ru.example.test.boot.services.LessonService;
import ru.example.test.boot.exceptions.StorageFileNotFoundException;
import ru.example.test.boot.services.interfaces.StorageService;


@RestController
@RequestMapping("/api/v1/files_lessons")
@RequiredArgsConstructor
public class FileDescriptorController {
    private final StorageService storageService;
    private final LessonService lessonService;

    @GetMapping
    public List<FileDescriptorDto> listUploadedFiles(Model model) {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileDescriptorController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        return storageService.findAll();
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);

    }

    @GetMapping("/{id}")
    public Optional<FileDescriptorDto> findFileById(@PathVariable Long id) {
        return storageService.findFileById(id);
    }

    @PostMapping("/")
    public RedirectView handleFileUpload(@RequestParam("file") MultipartFile file,
                                         @RequestParam(name = "lessonId") Long lessonId,
                                         @RequestParam(name = "filePath") String filePath,
                                         RedirectAttributes redirectAttributes) {
        Lesson lessonTarget = lessonService.findById(lessonId).orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
        storageService.store(lessonTarget, filePath, file);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return new RedirectView("/education/file_edit.html");
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}