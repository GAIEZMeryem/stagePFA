package com.example.gestionIndeminites.repository;



import org.springframework.data.jpa.repository.JpaRepository;


import com.example.gestionIndeminites.entity.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
	Utilisateur findByLogin(String login);
}
