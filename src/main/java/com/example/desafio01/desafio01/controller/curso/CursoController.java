package com.example.desafio01.desafio01.controller.curso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafio01.desafio01.model.curso.Curso;
import com.example.desafio01.desafio01.service.curso.CursoService;
import com.example.desafio01.desafio01.util.ConverterID;


@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping("")
    public ResponseEntity<String> cadastrarCurso(@RequestBody Curso curso){
        cursoService.cadastrarCurso(curso);
        return ResponseEntity.ok("Curso cadastrado com sucesso!");
    }

    @GetMapping("")
    public ResponseEntity<List<Curso>> listarCursos(){
        return ResponseEntity.ok(cursoService.listarCursos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Curso> cursoById(@PathVariable String id){
        return ResponseEntity.ok(cursoService.buscarPorId(ConverterID.convertStringToUUID(id)));
    }
    
    @PostMapping("/{id}")
    public ResponseEntity<String> editarCurso(@PathVariable String id, @RequestBody Curso curso){
        return ResponseEntity.ok(cursoService.editarCurso(ConverterID.convertStringToUUID(id), curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCurso(@PathVariable String id){
        return ResponseEntity.ok(cursoService.deletarCurso(ConverterID.convertStringToUUID(id)));
    }
    
    @PatchMapping("/{id}/ativo")
    public ResponseEntity<String> desativarCurso(@PathVariable String id){
        return ResponseEntity.ok(cursoService.desativarCurso(ConverterID.convertStringToUUID(id)));
    }

}
