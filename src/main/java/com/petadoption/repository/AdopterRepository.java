package com.petadoption.repository;

import com.petadoption.model.Adopter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdopterRepository extends JpaRepository<Adopter, Integer> {
}