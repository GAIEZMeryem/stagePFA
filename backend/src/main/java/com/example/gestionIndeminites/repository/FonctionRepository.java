package com.example.gestionIndeminites.repository;
import org.springframework.data.jpa.repository.JpaRepository;


import com.example.gestionIndeminites.entity.Fonction;
public interface FonctionRepository extends JpaRepository<Fonction, Integer> {
}
