package com.example.gestionIndeminites.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Fonction {
    public Fonction() {
		super();
	}

	public Fonction(int id, String titre, Double taux) {
		super();
		this.id = id;
		this.titre = titre;
		this.taux = taux;
	
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;
    private Double taux;

    @OneToMany(mappedBy = "fonction")
    private Set<Personnel> personnels;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Double getTaux() {
		return taux;
	}

	public void setTaux(Double taux) {
		this.taux = taux;
	}


  
}