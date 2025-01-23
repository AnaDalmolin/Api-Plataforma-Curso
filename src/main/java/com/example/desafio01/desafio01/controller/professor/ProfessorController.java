package com.example.desafio01.desafio01.controller.professor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafio01.desafio01.model.professor.Professor;
import com.example.desafio01.desafio01.service.professor.ProfessorService;
import com.example.desafio01.desafio01.util.ConverterID;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    
    @PostMapping("/cadastrar")
    public ResponseEntity<String> createProfessor(@RequestBody Professor professor) {
        boolean cadastro = professorService.createProfessor(professor);
        return cadastro ? ResponseEntity.status(HttpStatus.CREATED).body("Professor cadastrado com sucesso!") 
                        : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar professor");
    }

    @PostMapping("/logar")
    public ResponseEntity<String> loginProfessor(@RequestBody Professor professor) {
        String login = professorService.loginProfessor(professor);
        return login != null ? ResponseEntity.ok("Token: " + login) 
                            : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro ao logar professor");
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> updateProfessor(@PathVariable String id, @RequestBody Professor professor) {
        professor.setId(ConverterID.convertStringToUUID(id));
        professorService.editProfessor(professor);
        return ResponseEntity.ok("Professor atualizado com sucesso!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable String id) {
        Professor professor = professorService.getProfessorById(ConverterID.convertStringToUUID(id));
        return professor != null ? ResponseEntity.ok(professor) 
                                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Professor>> getAll() {
        List<Professor> professores = professorService.getAll();
        return professores != null && !professores.isEmpty() ? ResponseEntity.ok(professores) 
                                                            : ResponseEntity.status(HttpStatus.NO_CONTENT).body(professores);
    }
}
