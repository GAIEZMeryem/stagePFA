package com.example.gestionIndeminites.entity;


import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Personnel {
    public Personnel() {
		super();
	}

	public Personnel(int id, String nomComplet, String cin, String imt, Double taux, Fonction fonction,
			Services service) {
		super();
		this.id = id;
		this.nomComplet = nomComplet;
		this.cin = cin;
		this.imt = imt;
		this.taux = taux;
		this.fonction = fonction;
		this.service = service;
	
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nomComplet;
    private String cin;
    private String imt;
    private Double taux;

    @ManyToOne
    @JoinColumn(name = "fonction_id")
    private Fonction fonction;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Services service;

    @OneToMany(mappedBy = "personnel")
    private Set<PersonnelTableauGarde> personnelTableauGardes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomComplet() {
		return nomComplet;
	}

	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getImt() {
		return imt;
	}

	public void setImt(String imt) {
		this.imt = imt;
	}

	public Double getTaux() {
		return taux;
	}

	public void setTaux(Double taux) {
		this.taux = taux;
	}

	public Fonction getFonction() {
		return fonction;
	}

	public void setFonction(Fonction fonction) {
		this.fonction = fonction;
	}

	public Services getService() {
		return service;
	}

	public void setService(Services service) {
		this.service = service;
	}




 
}