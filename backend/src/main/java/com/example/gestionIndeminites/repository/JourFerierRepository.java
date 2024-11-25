package com.example.gestionIndeminites.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionIndeminites.entity.JourFerier;

public interface JourFerierRepository extends JpaRepository<JourFerier, Integer> {
	
}
