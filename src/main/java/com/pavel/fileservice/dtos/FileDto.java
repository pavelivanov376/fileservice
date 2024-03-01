package com.pavel.fileservice.dtos;

import org.springframework.web.multipart.MultipartFile;

public class FileDto {
    private MultipartFile content;

    public MultipartFile getContent() {
        return content;
    }

    public FileDto setContent(MultipartFile content) {
        this.content = content;
        return this;
    }

}
