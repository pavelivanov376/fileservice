package com.pavel.fileservice.dtos;

import org.springframework.web.multipart.MultipartFile;

public class FileDto {

    private String name;
    private MultipartFile content;

    public MultipartFile getContent() {
        return content;
    }

    public FileDto setContent(MultipartFile content) {
        this.content = content;
        return this;
    }

    public String getName() {
        return name;
    }

    public FileDto setName(String name) {
        this.name = name;
        return this;
    }
}
