package com.pavel.fileservice.services;

import com.pavel.fileservice.dtos.FileDto;
import com.pavel.fileservice.entities.FileEntity;
import com.pavel.fileservice.repositories.FileRepository;
import org.springframework.stereotype.Service;

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

    public UUID upload(FileDto fileDto) {

        FileEntity file = new FileEntity();
        file.setName(fileDto.getContent().getOriginalFilename());
        UUID uuid = UUID.randomUUID();
        file.setUuid(uuid.toString());

        fileRepository.save(file);

        return uuid;
    }

    public String delete(String uuid) {
        fileRepository.deleteByUuid(uuid);

        return "File Removed";
    }
}
