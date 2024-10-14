package com.example.demo.formbackend.service;

import com.example.demo.formbackend.model.FormStructure;
import com.example.demo.formbackend.repository.FormStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormStructureService {

    @Autowired
    private FormStructureRepository repository;

    public List<FormStructure> getAllFormStructures() {
        return repository.findAll();
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
}

