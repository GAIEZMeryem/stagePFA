package com.example.gestionIndeminites.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Services {
    public Services() {
		super();
	}

	public Services(int id, String titre) {
		super();
		this.id = id;
		this.titre = titre;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;

    @OneToMany(mappedBy = "service")
    private Set<Personnel> personnels;
    
    @ManyToOne
    @JoinColumn(name = "trimestre_id")
    private Trimestre trimestre;



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


}