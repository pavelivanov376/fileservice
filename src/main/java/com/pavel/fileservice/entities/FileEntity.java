package com.pavel.fileservice.entities;

import jakarta.persistence.*;

@Entity(name = "files")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type")
    private String type;

    @Transient
    private byte[] content;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @Column(name = "name", nullable = false)
    private String name;

    public String getType() {
        return type;
    }

    public FileEntity setType(String type) {
        this.type = type;
        return this;
    }

    public byte[] getContent() {
        return content;
    }

    public FileEntity setContent(byte[] content) {
        this.content = content;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public FileEntity setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public FileEntity setName(String name) {
        this.name = name;
        return this;
    }
}
