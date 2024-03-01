package com.pavel.fileservice.controllers;

import com.pavel.fileservice.dtos.FileDto;
import com.pavel.fileservice.entities.FileEntity;
import com.pavel.fileservice.services.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.UUID;

@RestController
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @GetMapping("/api/download/{uuid}")
    public ResponseEntity<FileEntity> downloadFile(@PathVariable("uuid") String uuid) throws IOException {
        FileEntity file = fileService.findByUuid(uuid);
        return ResponseEntity.ok()
                .body(file);
    }

    @PostMapping("/api/upload")
    public ResponseEntity<UUID> uploadFile(FileDto fileDto) throws IOException {
        UUID uuid =  fileService.upload(fileDto);

        return ResponseEntity.ok(uuid);
    }
    @DeleteMapping("/api/file/{uuid}")
    public ResponseEntity<String> deleteFile(@PathVariable("uuid") String uuid) throws IOException {
        return ResponseEntity.ok(fileService.delete(uuid));
    }
}
