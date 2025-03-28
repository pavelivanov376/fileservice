package com.pavel.fileservice.services;

import com.pavel.fileservice.entities.FileEntity;
import com.pavel.fileservice.repositories.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileEntity findByUuid(String uuid) {
        return fileRepository.findByUuid(uuid);
    }

    public UUID upload(MultipartFile fileDto) throws IOException {
        UUID uuid = UUID.randomUUID();
        FileEntity file = new FileEntity()
                .setName(fileDto.getOriginalFilename())
                .setUuid(uuid.toString())
                .setContent(fileDto.getBytes());
        fileRepository.save(file);

        return uuid;
    }

    public String delete(String uuid) {
        fileRepository.deleteByUuid(uuid);

        return "File Removed";
    }
}
