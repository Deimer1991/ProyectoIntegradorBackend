// ProfesorRepository.java
package com.example.sistemadenotas.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemadenotas.model.entity.Profesor;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {}