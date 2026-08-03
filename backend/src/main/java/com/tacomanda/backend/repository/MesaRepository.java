package com.tacomanda.backend.repository;

import com.tacomanda.backend.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    Optional<Mesa> findByNumero(Integer numero);
}
