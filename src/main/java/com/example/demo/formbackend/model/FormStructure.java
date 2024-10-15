package com.example.demo.formbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "form_structures")
public class FormStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the form (can be duplicate)
    @NotBlank(message = "Form name is required.")
    @Column(nullable = false)
    private String name;

    // Timestamp when the form was created
    @Column(nullable = false)
    private LocalDateTime dateCreated;

    // Assuming form structure is stored as JSON
    @NotBlank(message = "Form structure JSON is required.")
    @Lob
    @Column(nullable = false)
    private String structureJson;

    // Constructors

    public FormStructure() {
    }

    public FormStructure(Long id, String name, LocalDateTime dateCreated, String structureJson) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.structureJson = structureJson;
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

    public String getStructureJson() {
        return structureJson;
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

    public void setStructureJson(String structureJson) {
        this.structureJson = structureJson;
    }

    // Lifecycle Callbacks

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
    }

    // Optional: toString, equals, and hashCode methods
}
