package com.example.demo.formbackend.repository;

import com.example.demo.formbackend.model.FormStructure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormStructureRepository extends JpaRepository<FormStructure, Long> {

    /**
     * Finds all FormStructures where the name contains the specified string (case-insensitive).
     *
     * @param name     The name or partial name to search for.
     * @param pageable Pagination information.
     * @return A page of FormStructures matching the search criteria.
     */
    Page<FormStructure> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
