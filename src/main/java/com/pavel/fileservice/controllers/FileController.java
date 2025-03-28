package com.pavel.fileservice.controllers;

import com.pavel.fileservice.entities.FileEntity;
import com.pavel.fileservice.services.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/api/download/{uuid}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("uuid") String uuid) throws IOException {
        FileEntity file = fileService.findByUuid(uuid);
        InputStream stream = new ByteArrayInputStream(file.getContent());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                .body(new InputStreamResource(stream));
    }

    @PostMapping("/api/upload")
    public ResponseEntity<UUID> uploadFile(@RequestParam("file") MultipartFile fileDto) throws IOException {
        UUID uuid = fileService.upload(fileDto);

        return ResponseEntity.ok(uuid);
    }

    @DeleteMapping("/api/file/{uuid}")
    public ResponseEntity<String> deleteFile(@PathVariable("uuid") String uuid) throws IOException {
        return ResponseEntity.ok(fileService.delete(uuid));
    }
}
