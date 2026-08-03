package com.tacomanda.backend.repository;

import com.tacomanda.backend.model.EncuestaCierre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EncuestaCierreRepository extends JpaRepository<EncuestaCierre, Integer> {
    List<EncuestaCierre> findAllByOrderByFechaHoraDesc();
}
