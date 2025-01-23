package com.example.desafio01.desafio01.service.curso;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.desafio01.desafio01.exceptions.CursoException;
import com.example.desafio01.desafio01.model.curso.Curso;
import com.example.desafio01.desafio01.repository.curso.CursoRepository;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    private static final String CURSO_NAO_ENCONTRADO = "Curso não encontrado";
    private static final String ERRO_AO_PROCESSAR = "Erro ao processar curso: ";

    public String cadastrarCurso(final Curso curso) {
        try {
            cursoRepository.save(curso);
            return "Curso cadastrado com sucesso!";
        } catch (Exception e) {
            throw new CursoException(ERRO_AO_PROCESSAR + e.getMessage());
        }
    }

    public String editarCurso(final UUID id, final Curso curso) {
        try {
            Curso cursoExistente = cursoRepository.findById(id)
                    .orElseThrow(() -> new CursoException(CURSO_NAO_ENCONTRADO));

            cursoExistente.setNome(curso.getNome());
            cursoExistente.setCategoria(curso.getCategoria());

            cursoRepository.save(cursoExistente);
            return "Curso editado com sucesso!";
        } catch (Exception e) {
            throw new CursoException(ERRO_AO_PROCESSAR + e.getMessage());
        }
    }

    public List<Curso> listarCursos() {
        try {
            return cursoRepository.findAll();
        } catch (Exception e) {
            throw new CursoException(ERRO_AO_PROCESSAR + e.getMessage());
        }
    }

    public Curso buscarPorId(final UUID id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new CursoException(CURSO_NAO_ENCONTRADO));
    }

    public String desativarCurso(final UUID id) {
        try {
            Curso curso = cursoRepository.findById(id)
                    .orElseThrow(() -> new CursoException(CURSO_NAO_ENCONTRADO));

            curso.setAtivo(false);
            cursoRepository.save(curso);
            return "Curso desativado com sucesso!";
        } catch (Exception e) {
            throw new CursoException(ERRO_AO_PROCESSAR + e.getMessage());
        }
    }

    public String deletarCurso(final UUID id) {
        try {
            if (!cursoRepository.existsById(id)) {
                throw new CursoException(CURSO_NAO_ENCONTRADO);
            }
            cursoRepository.deleteById(id);
            return "Curso deletado com sucesso!";
        } catch (Exception e) {
            throw new CursoException(ERRO_AO_PROCESSAR + e.getMessage());
        }
    }
}
