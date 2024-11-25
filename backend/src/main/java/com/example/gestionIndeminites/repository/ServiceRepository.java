package com.example.gestionIndeminites.repository;

import com.example.gestionIndeminites.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Services, Integer> {
    List<Services> findByTrimestreId(int trimestreId);
}
