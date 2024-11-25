package com.example.gestionIndeminites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.gestionIndeminites.entity.TableauGarde;
import com.example.gestionIndeminites.entity.Trimestre;

public interface TableauGardeRepository extends JpaRepository<TableauGarde, Integer> {
	boolean existsByAnneeAndTrimestre_IdAndMoisAndServiceId(int annee, int trimestreId, int mois, int serviceId);
	 List<TableauGarde> findByTrimestre(Trimestre trimestre);
	 
	
}
