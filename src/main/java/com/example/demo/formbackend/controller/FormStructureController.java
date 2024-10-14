package com.example.demo.formbackend.controller;


import com.example.demo.formbackend.model.FormStructure;
import com.example.demo.formbackend.service.FormStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/form-structures")
@CrossOrigin(origins = "*") // Allow cross-origin requests, adjust as needed
public class FormStructureController {

    @Autowired
    private FormStructureService service;

    // Get all form structures
    @GetMapping
    public List<FormStructure> getAllFormStructures() {
        return service.getAllFormStructures();
    }

    // Get a specific form structure by ID
    @GetMapping("/{id}")
    public ResponseEntity<FormStructure> getFormStructureById(@PathVariable Long id) {
        Optional<FormStructure> formStructure = service.getFormStructureById(id);
        return formStructure.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new form structure
    @PostMapping
    public ResponseEntity<FormStructure> createFormStructure(@RequestBody FormStructure formStructure) {
        FormStructure savedFormStructure = service.saveFormStructure(formStructure);
        return new ResponseEntity<>(savedFormStructure, HttpStatus.CREATED);
    }

    // Update an existing form structure
    @PutMapping("/{id}")
    public ResponseEntity<FormStructure> updateFormStructure(@PathVariable Long id,
                                                             @RequestBody FormStructure formStructure) {
        Optional<FormStructure> existingFormStructure = service.getFormStructureById(id);
        if (existingFormStructure.isPresent()) {
            FormStructure updatedFormStructure = existingFormStructure.get();
            updatedFormStructure.setStructureJson(formStructure.getStructureJson());
            // Update other fields as necessary
            service.saveFormStructure(updatedFormStructure);
            return ResponseEntity.ok(updatedFormStructure);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a form structure
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormStructure(@PathVariable Long id) {
        Optional<FormStructure> existingFormStructure = service.getFormStructureById(id);
        if (existingFormStructure.isPresent()) {
            service.deleteFormStructure(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
