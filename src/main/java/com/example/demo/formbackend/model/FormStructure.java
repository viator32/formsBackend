package com.example.demo.formbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "form_structures")
public class FormStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Assuming form structure is stored as JSON
    @Lob
    @Column(nullable = false)
    private String structureJson;

    // Constructors
    public FormStructure() {
    }

    public FormStructure(Long id, String structureJson) {
        this.id = id;
        this.structureJson = structureJson;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStructureJson() {
        return structureJson;
    }

    public void setStructureJson(String structureJson) {
        this.structureJson = structureJson;
    }

    // Additional getters and setters for other fields if any
}
