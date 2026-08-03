package com.tacomanda.backend.repository;

import com.tacomanda.backend.model.PersonaTacomanda;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Ya no necesita un findBy... a la medida: el no_control ES la llave
 * primaria, así que el login busca directo con findById(noControl).
 */
public interface UsuarioRepository extends JpaRepository<PersonaTacomanda, Integer> {
}
