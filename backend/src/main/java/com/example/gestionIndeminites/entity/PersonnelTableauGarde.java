package com.example.gestionIndeminites.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jakarta.persistence.*;

@Entity
public class PersonnelTableauGarde {
    public PersonnelTableauGarde(String dateGarde, int taux, int igr,int etatGarde,
			 TableauGarde tableau, int numero) {
		super();

		this.dateGarde = dateGarde;
		this.etatGarde = etatGarde;
		this.numero = numero;
		this.tableau = tableau;
	}

	public PersonnelTableauGarde() {
		super();
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String dateGarde;
    private int taux;
    private int igr;
    private int etatGarde;
    private int numero ;
    

    @ManyToOne
    @JoinColumn(name = "personnel_id")
    private Personnel personnel;

    @ManyToOne
    @JoinColumn(name = "tableau_id")
    private TableauGarde tableau;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDateGarde() {
		return dateGarde;
	}

	public void setDateGarde(String dateGarde) {
		this.dateGarde = dateGarde;
	}

	public int getTaux() {
		return taux;
	}

	public void setTaux(int taux) {
		this.taux = taux;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public TableauGarde getTableau() {
		return tableau;
	}

	public void setTableau(TableauGarde tableau) {
		this.tableau = tableau;
	}

	public int getIgr() {
		return igr;
	}

	public void setIGR(int igr) {
		this.igr = igr;
	}
	public int getEtatGarde() {
		return etatGarde;
	}

	public void setEtatGarde(int etatGarde) {
		this.etatGarde = etatGarde;
	}
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	public LocalDate getDateGardeAsLocalDate() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
	    try {
	        return LocalDate.parse(this.dateGarde, formatter);
	    } catch (DateTimeParseException e) {
	        throw new IllegalArgumentException("Invalid date format for dateGarde: " + this.dateGarde, e);
	    }
	

  
	}
}
