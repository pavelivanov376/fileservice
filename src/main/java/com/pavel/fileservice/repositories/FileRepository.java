package com.pavel.fileservice.repositories;

import com.pavel.fileservice.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findByName(String name);

    FileEntity findByUuid(String uuid);

    @Transactional
    void deleteByUuid(String uuid);
}