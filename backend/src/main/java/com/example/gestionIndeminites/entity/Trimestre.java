package com.example.gestionIndeminites.entity;



import jakarta.persistence.*;
import java.util.List;

@Entity
public class Trimestre {
    public Trimestre() {
		super();
	}
	public Trimestre( int numeroTrimestre, int annee,
			int etatTrimestre) {
		super();
		this.numeroTrimestre = numeroTrimestre;
		this.annee = annee;
		this.etatTrimestre = etatTrimestre;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int numeroTrimestre;
    private int annee;
 
    private int etatTrimestre;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "trimestre_service",
        joinColumns = @JoinColumn(name = "trimestre_id"),
        inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Services> services;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNumeroTrimestre() { return numeroTrimestre; }
    public void setNumeroTrimestre(int numeroTrimestre) { this.numeroTrimestre = numeroTrimestre; }

    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }

    
    
	public int getEtatTrimestre() {
		return etatTrimestre;
	}
	public void setEtatTrimestre(int etatTrimestre) {
		this.etatTrimestre = etatTrimestre;
	}

    
}
