package com.example.gestionIndeminites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestionIndeminites.entity.PersonnelTableauGarde;


public interface PersonnelTableauGardeRepository extends JpaRepository<PersonnelTableauGarde, Integer> {
	List<PersonnelTableauGarde> findByTableauId(int tableauId);
	boolean existsByTableauId(int tableauId);


}

