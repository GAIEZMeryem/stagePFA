package com.example.gestionIndeminites.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class TableauGarde {
    public TableauGarde() {
		super();
	}

	public TableauGarde(int id, int mois, int annee, int nombrePersonnelGarde, String observation, String typeGarde,
			Trimestre trimestre, Services service) {
		super();
		this.id = id;
		this.mois = mois;
		this.annee = annee;
		this.nombrePersonnelGarde = nombrePersonnelGarde;
		this.observation = observation;
		this.typeGarde = typeGarde;
		this.trimestre = trimestre;
		this.service = service;
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int mois;
    private int annee;
    private int nombrePersonnelGarde;
    private String observation;
    private String typeGarde;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trimestre_id")
    private Trimestre trimestre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Services service;

    @OneToMany(mappedBy = "tableau")
    private Set<PersonnelTableauGarde> personnelTableauGardes;
    
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMois() {
		return mois;
	}

	public void setMois(int mois) {
		this.mois = mois;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public int getNombrePersonnelGarde() {
		return nombrePersonnelGarde;
	}

	public void setNombrePersonnelGarde(int nombrePersonnelGarde) {
		this.nombrePersonnelGarde = nombrePersonnelGarde;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getTypeGarde() {
		return typeGarde;
	}

	public void setTypeGarde(String typeGarde) {
		this.typeGarde = typeGarde;
	}

	public Trimestre getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(Trimestre trimestre) {
		this.trimestre = trimestre;
	}

	public Services getService() {
		return service;
	}

	public void setService(Services service) {
		this.service = service;
	}
	@Override
	public String toString() {
	    return "TableauGarde{id=" + id + ", typeGarde='" + typeGarde + "', annee=" + annee + "}";
	}


}
