package com.example.gestionIndeminites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.gestionIndeminites.entity.Personnel;

public interface PersonnelRepository extends JpaRepository<Personnel, Integer> {
	List<Personnel> findByServiceId(int serviceId);

}
