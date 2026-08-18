package com.davi.api.controllers;

import com.davi.api.dtos.CursoRequestDTO;
import com.davi.api.dtos.CursoUpdateDTO;
import com.davi.api.models.Curso;
import com.davi.api.repositories.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<Curso> createCurso(@Valid @RequestBody CursoRequestDTO cursoDTO){

       Curso curso = new Curso();
       curso.setName(cursoDTO.name());
       curso.setCategory(cursoDTO.category());
       curso.setProfessor(cursoDTO.professor());

       Curso cursoSalvo = cursoRepository.save(curso);

       return ResponseEntity.status(HttpStatus.CREATED).body(cursoSalvo);

    }

    @GetMapping
    public ResponseEntity<List<Curso>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {

        if (name != null && category != null) {
            List<Curso> cursosFiltrados = cursoRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name, category);

            return ResponseEntity.ok(cursosFiltrados);
        }

        List<Curso> todosCursos = cursoRepository.findAll();
        return ResponseEntity.ok(todosCursos);
    }

    @PutMapping("/atualiza/{id}")
    public ResponseEntity<Curso> updateCurso(
            @PathVariable UUID id,
            @RequestBody CursoUpdateDTO dto) {

        var cursoOptional = cursoRepository.findById(id);

        if (cursoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }


        Curso curso = cursoOptional.get();

        if (dto.name() != null) {
            curso.setName(dto.name());
        }
        if (dto.category() != null) {
            curso.setCategory(dto.category());
        }
        if (dto.professor() != null) {
            curso.setProfessor(dto.professor());
        }

        Curso cursoAtualizado = cursoRepository.save(curso);

        return ResponseEntity.ok(cursoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable UUID id) {

        if (!cursoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        cursoRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<Curso> toggleActive(@PathVariable UUID id) {

        var cursoOptional = cursoRepository.findById(id);

        if (cursoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso curso = cursoOptional.get();

        curso.setActive(!curso.getActive());
        Curso cursoAtualizado = cursoRepository.save(curso);


        return ResponseEntity.ok(cursoAtualizado);
    }
}
