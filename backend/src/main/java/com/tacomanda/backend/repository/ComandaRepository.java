package com.tacomanda.backend.repository;

import com.tacomanda.backend.model.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComandaRepository extends JpaRepository<Comanda, Integer> {
    List<Comanda> findByEstadoOrderByFechaAsc(String estado);
}
