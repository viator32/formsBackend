package com.example.demo.formbackend.service;

import com.example.demo.formbackend.model.FormStructure;
import com.example.demo.formbackend.repository.FormStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FormStructureService {

    @Autowired
    private FormStructureRepository repository;

    public Page<FormStructure> getAllFormStructures(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<FormStructure> getFormStructureById(Long id) {
        return repository.findById(id);
    }

    public FormStructure saveFormStructure(FormStructure formStructure) {
        return repository.save(formStructure);
    }

    public void deleteFormStructure(Long id) {
        repository.deleteById(id);
    }

    /**
     * Searches for FormStructures by name with pagination.
     *
     * @param name     The name or partial name to search for.
     * @param pageable Pagination information.
     * @return A page of FormStructures matching the search criteria.
     */
    public Page<FormStructure> searchFormStructuresByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }
}
