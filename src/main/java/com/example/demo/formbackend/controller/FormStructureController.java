package com.example.demo.formbackend.controller;

import com.example.demo.formbackend.dto.FormSummaryDTO;
import com.example.demo.formbackend.model.FormStructure;
import com.example.demo.formbackend.service.FormStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/form-structures")
@CrossOrigin(origins = "*") // Adjust origins as needed
public class FormStructureController {

    @Autowired
    private FormStructureService service;

    /**
     * Retrieves all form structures with pagination.
     *
     * @param page Page number (default 0).
     * @param size Number of records per page (default 10).
     * @return A paginated list of form structures.
     */
    @GetMapping
    public Page<FormStructure> getAllFormStructures(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateCreated").descending());
        return service.getAllFormStructures(pageable);
    }

    /**
     * Retrieves a specific form structure by ID.
     *
     * @param id The ID of the form structure.
     * @return The form structure if found, else 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FormStructure> getFormStructureById(@PathVariable Long id) {
        Optional<FormStructure> formStructure = service.getFormStructureById(id);
        return formStructure.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new form structure.
     *
     * @param formStructure The form structure to create.
     * @return The created form structure with status 201.
     */
    @PostMapping
    public ResponseEntity<FormStructure> createFormStructure(@Valid @RequestBody FormStructure formStructure) {
        FormStructure savedFormStructure = service.saveFormStructure(formStructure);
        return new ResponseEntity<>(savedFormStructure, HttpStatus.CREATED);
    }

    /**
     * Updates an existing form structure by ID.
     *
     * @param id            The ID of the form structure to update.
     * @param formStructure The updated form structure data.
     * @return The updated form structure if found, else 404.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FormStructure> updateFormStructure(@PathVariable Long id,
                                                             @Valid @RequestBody FormStructure formStructure) {
        Optional<FormStructure> existingFormStructure = service.getFormStructureById(id);
        if (existingFormStructure.isPresent()) {
            FormStructure updatedFormStructure = existingFormStructure.get();
            updatedFormStructure.setName(formStructure.getName());
            updatedFormStructure.setStructureJson(formStructure.getStructureJson());
            // Note: dateCreated is not updated
            service.saveFormStructure(updatedFormStructure);
            return ResponseEntity.ok(updatedFormStructure);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a form structure by ID.
     *
     * @param id The ID of the form structure to delete.
     * @return 204 No Content if deleted, else 404.
     */
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

    /**
     * Searches form structures by name with pagination.
     *
     * @param name The name or partial name to search for.
     * @param page Page number (default 0).
     * @param size Number of records per page (default 10).
     * @return A paginated list of form structures matching the search criteria.
     */
    @GetMapping("/search")
    public Page<FormStructure> searchFormStructuresByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateCreated").descending());
        return service.searchFormStructuresByName(name, pageable);
    }

    /**
     * Fetches all forms without their JSON structures.
     *
     * @return A list of form summaries.
     */
    @GetMapping("/summary")
    public ResponseEntity<List<FormSummaryDTO>> getAllFormSummaries() {
        List<FormStructure> forms = service.getAllFormStructures(Pageable.unpaged()).getContent();
        List<FormSummaryDTO> summaries = forms.stream()
                .map(form -> new FormSummaryDTO(form.getId(), form.getName(), form.getDateCreated()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(summaries);
    }
}
