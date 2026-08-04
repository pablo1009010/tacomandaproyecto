package com.tacomanda.backend.repository;

import com.tacomanda.backend.model.PersonaTacomanda;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<PersonaTacomanda, Integer> {
}
