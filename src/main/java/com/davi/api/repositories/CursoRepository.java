package com.davi.api.repositories;

import com.davi.api.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CursoRepository extends JpaRepository<Curso, UUID> {

    List<Curso> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category);

}