// AdministradorRepository.java
package com.example.sistemadenotas.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemadenotas.model.entity.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {}
