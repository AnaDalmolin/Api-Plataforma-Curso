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

    public Boolean createProfessor(Professor professor) {
        
        try{

            var senhaCriptografada = passwordEncoder.encode(professor.getSenha());

            professor.setSenha(senhaCriptografada);

            professorRepository.save(professor);
            
            return true;

        }catch(Exception e){

            throw new ProfessorServerException( "Erro ao salvar professor", e.getMessage());
        
        }
    }

    public Boolean editProfessor(Professor professor) {
        try {
            Professor existingProfessor = professorRepository.findById(professor.getId())
                .orElseThrow(() -> 
                new ProfessorServerException("Erro ao atualizar professor", "Professor não encontrado"));

            existingProfessor.setNome(professor.getNome());
            existingProfessor.setEmail(professor.getEmail());
            if (professor.getSenha() != null && !professor.getSenha().isEmpty()) {
                var senhaCriptografada = passwordEncoder.encode(professor.getSenha());
                existingProfessor.setSenha(senhaCriptografada);
            }

            professorRepository.save(existingProfessor);
            return true;
        } catch (Exception e) {
            throw new ProfessorServerException("Erro ao atualizar professor", e.getMessage());
        }
    }

    public String loginProfessor(Professor professor) {
        
        try{
            
            var professorEncontrado = professorRepository.findByEmail(professor.getEmail())
                .orElseThrow(() -> {
                    throw new ProfessorServerException( "Erro ao logar professor", "Professor não encontrado");
                });
            
            var senhaCorreta = passwordEncoder.matches(professor.getSenha(), professorEncontrado.getSenha());
            if (!senhaCorreta) {
                throw new ProfessorServerException( "Erro ao logar professor", "Senha incorreta");
            }

            professor.setId(professorEncontrado.getId());
            professor.setNome(professorEncontrado.getNome());
            
            var token = jwtUtil.generateToken(professor.getEmail());
            return token;

        }catch(Exception e){

            throw new ProfessorServerException( "Erro ao logar professor", e.getMessage());
        
        }
    }

    public Professor getProfessorById(UUID id) {
        try {
            
            Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ProfessorServerException("Erro ao buscar professor", "Professor não encontrado"));
            
            return professor;
            
        } catch (Exception e) {

            throw new ProfessorServerException("Erro ao buscar professor", e.getMessage());
        }
    }
    
    public List<Professor> getAll() {
        try {
            
            List<Professor> professors = professorRepository.findAll();
            
            if (professors.isEmpty()) {
                throw new ProfessorServerException("Erro ao buscar professores", "Nenhum professor encontrado");
            }
            
            return professors;
            
        } catch (Exception e) {
    
            throw new ProfessorServerException("Erro ao buscar professor", e.getMessage());
        }
    }

}
