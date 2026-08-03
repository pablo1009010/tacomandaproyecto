package com.tacomanda.backend.repository;

import com.tacomanda.backend.model.Platillo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatilloRepository extends JpaRepository<Platillo, Integer> {
}
