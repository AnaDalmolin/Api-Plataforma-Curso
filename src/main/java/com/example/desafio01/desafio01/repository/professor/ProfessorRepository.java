package com.example.desafio01.desafio01.repository.professor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafio01.desafio01.model.professor.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, UUID> {
    
    Optional<Professor> findByEmail(String email);

    Optional<Professor> findById(UUID id);

    List<Professor> findAll();
    
}
