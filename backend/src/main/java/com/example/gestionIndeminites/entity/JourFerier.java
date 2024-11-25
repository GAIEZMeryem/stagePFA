package com.example.gestionIndeminites.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class JourFerier {
    public JourFerier() {
		super();
	}
	public JourFerier(int id, String titre, LocalDate date) {
		super();
		this.id = id;
		this.titre = titre;
		this.date = date;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;
    private LocalDate date;
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
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}

}
