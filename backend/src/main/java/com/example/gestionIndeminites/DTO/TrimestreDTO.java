package com.example.gestionIndeminites.DTO;


import java.util.List;

public class TrimestreDTO {
    private int numeroTrimestre;
    private int annee;
    private double montantNetTotal;
    private double montantTresor;
    private List<Integer> servicesIds;
    private int etatTrimestre;
    


    public int getNumeroTrimestre() { return numeroTrimestre; }
    public void setNumeroTrimestre(int numeroTrimestre) { this.numeroTrimestre = numeroTrimestre; }

    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }

    public double getMontantNetTotal() { return montantNetTotal; }
    public void setMontantNetTotal(double montantNetTotal) { this.montantNetTotal = montantNetTotal; }

    public double getMontantTresor() { return montantTresor; }
    public void setMontantTresor(double montantTresor) { this.montantTresor = montantTresor; }

    public List<Integer> getServicesIds() { return servicesIds; }
    public void setServicesIds(List<Integer> serviceIds) { this.servicesIds = serviceIds; }
    public int getEtatTrimestre() {
		return etatTrimestre;
	}
	public void setEtatTrimestre(int etatTrimestre) {
		this.etatTrimestre = etatTrimestre;
	}

    
}