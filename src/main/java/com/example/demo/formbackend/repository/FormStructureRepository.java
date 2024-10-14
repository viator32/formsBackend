package com.example.demo.formbackend.repository;


import com.example.demo.formbackend.model.FormStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormStructureRepository extends JpaRepository<FormStructure, Long> {
    // Additional query methods can be defined here
}

