package com.example.demo.formbackend.dto;

import java.time.LocalDateTime;

public class FormSummaryDTO {
    private Long id;
    private String name;
    private LocalDateTime dateCreated;

    public FormSummaryDTO() {
    }

    public FormSummaryDTO(Long id, String name, LocalDateTime dateCreated) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}
