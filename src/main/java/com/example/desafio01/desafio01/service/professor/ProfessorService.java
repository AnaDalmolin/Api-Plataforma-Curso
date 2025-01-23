package com.example.desafio01.desafio01.service.professor;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.desafio01.desafio01.exceptions.ProfessorServerException;
import com.example.desafio01.desafio01.model.professor.Professor;
import com.example.desafio01.desafio01.repository.professor.ProfessorRepository;
import com.example.desafio01.desafio01.security.JwtUtil;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String PROFESSOR_NAO_ENCONTRADO = "Professor não encontrado";
    private static final String ERRO_AO_SALVAR = "Erro ao salvar professor";
    private static final String ERRO_AO_ATUALIZAR = "Erro ao atualizar professor";
    private static final String ERRO_AO_LOGAR = "Erro ao logar professor";
    private static final String ERRO_AO_BUSCAR = "Erro ao buscar professor";

    public Boolean createProfessor(final Professor professor) {
        try {
            professor.setSenha(encryptPassword(professor.getSenha()));
            professorRepository.save(professor);
            return true;
        } catch (Exception e) {
            throw new ProfessorServerException(ERRO_AO_SALVAR, e.getMessage());
        }
    }

    public Boolean editProfessor(final Professor professor) {
        try {
            Professor existingProfessor = professorRepository.findById(professor.getId())
                    .orElseThrow(() -> new ProfessorServerException(ERRO_AO_ATUALIZAR, PROFESSOR_NAO_ENCONTRADO));

            existingProfessor.setNome(professor.getNome());
            existingProfessor.setEmail(professor.getEmail());

            if (professor.getSenha() != null && !professor.getSenha().isEmpty()) {
                existingProfessor.setSenha(encryptPassword(professor.getSenha()));
            }

            professorRepository.save(existingProfessor);
            return true;
        } catch (Exception e) {
            throw new ProfessorServerException(ERRO_AO_ATUALIZAR, e.getMessage());
        }
    }

    public String loginProfessor(final Professor professor) {
        try {
            Professor foundProfessor = professorRepository.findByEmail(professor.getEmail())
                    .orElseThrow(() -> new ProfessorServerException(ERRO_AO_LOGAR, PROFESSOR_NAO_ENCONTRADO));

            if (!passwordEncoder.matches(professor.getSenha(), foundProfessor.getSenha())) {
                throw new ProfessorServerException(ERRO_AO_LOGAR, "Senha incorreta");
            }

            return jwtUtil.generateToken(foundProfessor.getEmail());
        } catch (Exception e) {
            throw new ProfessorServerException(ERRO_AO_LOGAR, e.getMessage());
        }
    }

    public Professor getProfessorById(final UUID id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new ProfessorServerException(ERRO_AO_BUSCAR, PROFESSOR_NAO_ENCONTRADO));
    }

    public List<Professor> getAll() {
        try {
            List<Professor> professors = professorRepository.findAll();

            if (professors.isEmpty()) {
                throw new ProfessorServerException(ERRO_AO_BUSCAR, "Nenhum professor encontrado");
            }

            return professors;
        } catch (Exception e) {
            throw new ProfessorServerException(ERRO_AO_BUSCAR, e.getMessage());
        }
    }

    private String encryptPassword(final String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}