package com.example.desafio01.desafio01.repository.curso;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafio01.desafio01.model.curso.Curso;;


@Repository
public interface CursoRepository extends JpaRepository<Curso, UUID> {


    Optional<Curso> findById(UUID id);

    List<Curso> findAll();

    void deleteById(UUID id);
}
