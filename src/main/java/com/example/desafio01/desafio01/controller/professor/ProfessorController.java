package com.example.desafio01.desafio01.controller.professor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> createProfessor(@RequestBody Professor professor) {

        boolean cadastro = professorService.createProfessor(professor);

        if (cadastro) {

            return ResponseEntity.ok().body("Professor cadastrado com sucesso!");

        }else {

            return ResponseEntity.badRequest().body("Erro ao cadastrar professor");

        }

    }

    @PostMapping("/logar")
    public ResponseEntity<String> loginProfessor(@RequestBody Professor professor) {

        String login = professorService.loginProfessor(professor);

        if (login != null) {

            return ResponseEntity.ok().body("Token: " + login);

        }else {

            return ResponseEntity.badRequest().body("Erro ao logar professor");
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateProfessor(@PathVariable String id, @RequestBody Professor professor) {
        
        professor.setId(ConverterID.convertStringToUUID(id));
        
        professorService.editProfessor(professor);

        return ResponseEntity.ok().body("Professor atualizado com sucesso!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfessorById(@PathVariable String id) {

        Professor professor = professorService.getProfessorById(ConverterID.convertStringToUUID(id));

        if (professor != null) {

            return ResponseEntity.ok().body(professor);

        } else {

            return ResponseEntity.badRequest().body("Professores não encontrados");
        }

    }
    
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<Professor> professores=  professorService.getAll();

         if (professores != null) {

            return ResponseEntity.ok().body(professores);

        } else {

            return ResponseEntity.badRequest().body("Professores não encontrados");
        }
    }

    

}
