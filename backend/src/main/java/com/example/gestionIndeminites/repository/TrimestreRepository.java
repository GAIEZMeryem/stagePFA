package com.example.gestionIndeminites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.gestionIndeminites.entity.Trimestre;

public interface TrimestreRepository extends JpaRepository<Trimestre, Integer> {
	List<Trimestre> findByEtatTrimestre(int etatTrimestre);
	
}
