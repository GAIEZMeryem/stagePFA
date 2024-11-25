package com.example.gestionIndeminites.entity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FicheCalcule {
    private int serviceId;
    private String nomService;
    private int id;
    private String nomComplet;
    private String cin;
    private String imt;
    private double taux;
    private double tauxFrais;
    private String typeGarde;
    private Trimestre trimestre;
    private int numTrimestre;
    private int annee;
    private String t1;
    private int t1Num;
    private int t1NbrVend;
    private int t1NbrNormal;
    private int t1NbrNormalRamadan;
    private int t1NbrFerier;
    private int t1NbrNuit;

    private String t2;
    private int t2Num;
    private int t2NbrVend;
    private int t2NbrNormal;
    private int t2NbrNormalRamadan;
    private int t2NbrFerier;
    private int t2NbrNuit;

    private String t3;
    private int t3Num;
    private int t3NbrVend;
    private int t3NbrNormal;
    private int t3NbrNormalRamadan;
    private int t3NbrFerier;
    private int t3NbrNuit;

    public String getDateDuAu() {
        validateMonth(t1Num);
        validateMonth(t3Num);

        LocalDate dateDu = LocalDate.of(annee, t1Num, 1);
        LocalDate dateAu = LocalDate.of(annee, t3Num, dateDu.lengthOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        return dateDu.format(formatter) + " - " + dateAu.format(formatter);
    }

    public double getT1NombreHeure() {
        if ("permanence".equals(typeGarde)) {
            double sum = (t1NbrNormal * 16.5) + (t1NbrFerier * 24);
            return Math.min(sum, 120);
        }
        return (t1NbrNormalRamadan * 5.5) + (t1NbrVend * 5.5) + (t1NbrNormal * 4.5) + (t1NbrFerier * 12) + (t1NbrNuit * 12);
    }

    public double getT2NombreHeure() {
        if ("permanence".equals(typeGarde)) {
            double sum = (t2NbrNormal * 16.5) + (t2NbrFerier * 24);
            return Math.min(sum, 120);
        }
        return (t2NbrNormalRamadan * 5.5) + (t2NbrVend * 5.5) + (t2NbrNormal * 4.5) + (t2NbrFerier * 12) + (t2NbrNuit * 12);
    }

    public double getT3NombreHeure() {
        if ("permanence".equals(typeGarde)) {
            double sum = (t3NbrNormal * 16.5) + (t3NbrFerier * 24);
            return Math.min(sum, 120);
        }
        return (t3NbrNormalRamadan * 5.5) + (t3NbrVend * 5.5) + (t3NbrNormal * 4.5) + (t3NbrFerier * 12) + (t3NbrNuit * 12);
    }

    public double getTotalHeure() {
        return getT1NombreHeure() + getT2NombreHeure() + getT3NombreHeure();
    }

    public int getTotalHG() {
        if ("permanence".equals(typeGarde)) {
            return (int) getTotalHeure();
        }
        double result = getTotalHeure() / 12;
        return (int) result;
    }

    public double getBrut() {
        return getTotalHG() * taux;
    }

    public double getFraisProfessionnelles() {
        return getBrut() * 0.25;
    }

    public double getIGR() {
        return (getBrut() - getFraisProfessionnelles()) * tauxFrais / 100;
    }

    public double getNet() {
        return getBrut() - getIGR();
    }

    private void validateMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month number: " + month);
        }
    }

    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public String getNomService() { return nomService; }
    public void setNomService(String nomService) { this.nomService = nomService; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomComplet() { return nomComplet; }
    public void setNomComplet(String nomComplet) { this.nomComplet = nomComplet; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public String getImt() { return imt; }
    public void setImt(String imt) { this.imt = imt; }

    public double getTaux() { return taux; }
    public void setTaux(double taux) { this.taux = taux; }

    public double getTauxFrais() { return tauxFrais; }
    public void setTauxFrais(double tauxFrais) { this.tauxFrais = tauxFrais; }

    public String getTypeGarde() { return typeGarde; }
    public void setTypeGarde(String typeGarde) { this.typeGarde = typeGarde; }

    public Trimestre getTrimestre() { return trimestre; }
    public void setTrimestre(Trimestre trimestre) { this.trimestre = trimestre; }

    public int getNumTrimestre() { return numTrimestre; }
    public void setNumTrimestre(int numTrimestre) { this.numTrimestre = numTrimestre; }

    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }

    public String getT1() { return t1; }
    public void setT1(String t1) { this.t1 = t1; }

    public int getT1Num() { return t1Num; }
    public void setT1Num(int t1Num) { 
        validateMonth(t1Num);
        this.t1Num = t1Num; 
    }

    public int getT1NbrVend() { return t1NbrVend; }
    public void setT1NbrVend(int t1NbrVend) { this.t1NbrVend = t1NbrVend; }

    public int getT1NbrNormal() { return t1NbrNormal; }
    public void setT1NbrNormal(int t1NbrNormal) { this.t1NbrNormal = t1NbrNormal; }

    public int getT1NbrNormalRamadan() { return t1NbrNormalRamadan; }
    public void setT1NbrNormalRamadan(int t1NbrNormalRamadan) { this.t1NbrNormalRamadan = t1NbrNormalRamadan; }

    public int getT1NbrFerier() { return t1NbrFerier; }
    public void setT1NbrFerier(int t1NbrFerier) { this.t1NbrFerier = t1NbrFerier; }

    public int getT1NbrNuit() { return t1NbrNuit; }
    public void setT1NbrNuit(int t1NbrNuit) { this.t1NbrNuit = t1NbrNuit; }

    public String getT2() { return t2; }
    public void setT2(String t2) { this.t2 = t2; }

    public int getT2Num() { return t2Num; }
    public void setT2Num(int t2Num) { 
        validateMonth(t2Num);
        this.t2Num = t2Num; 
    }

    public int getT2NbrVend() { return t2NbrVend; }
    public void setT2NbrVend(int t2NbrVend) { this.t2NbrVend = t2NbrVend; }

    public int getT2NbrNormal() { return t2NbrNormal; }
    public void setT2NbrNormal(int t2NbrNormal) { this.t2NbrNormal = t2NbrNormal; }

    public int getT2NbrNormalRamadan() { return t2NbrNormalRamadan; }
    public void setT2NbrNormalRamadan(int t2NbrNormalRamadan) { this.t2NbrNormalRamadan = t2NbrNormalRamadan; }

    public int getT2NbrFerier() { return t2NbrFerier; }
    public void setT2NbrFerier(int t2NbrFerier) { this.t2NbrFerier = t2NbrFerier; }

    public int getT2NbrNuit() { return t2NbrNuit; }
    public void setT2NbrNuit(int t2NbrNuit) { this.t2NbrNuit = t2NbrNuit; }

    public String getT3() { return t3; }
    public void setT3(String t3) { this.t3 = t3; }

    public int getT3Num() { return t3Num; }
    public void setT3Num(int t3Num) { 
        validateMonth(t3Num);
        this.t3Num = t3Num; 
    }

    public int getT3NbrVend() { return t3NbrVend; }
    public void setT3NbrVend(int t3NbrVend) { this.t3NbrVend = t3NbrVend; }

    public int getT3NbrNormal() { return t3NbrNormal; }
    public void setT3NbrNormal(int t3NbrNormal) { this.t3NbrNormal = t3NbrNormal; }

    public int getT3NbrNormalRamadan() { return t3NbrNormalRamadan; }
    public void setT3NbrNormalRamadan(int t3NbrNormalRamadan) { this.t3NbrNormalRamadan = t3NbrNormalRamadan; }

    public int getT3NbrFerier() { return t3NbrFerier; }
    public void setT3NbrFerier(int t3NbrFerier) { this.t3NbrFerier = t3NbrFerier; }

    public int getT3NbrNuit() { return t3NbrNuit; }
    public void setT3NbrNuit(int t3NbrNuit) { this.t3NbrNuit = t3NbrNuit; }
}
