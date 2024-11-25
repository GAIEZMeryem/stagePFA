package com.example.gestionIndeminites.service;

import com.example.gestionIndeminites.entity.*;
import com.example.gestionIndeminites.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class FicheCalculeService {

    @Autowired
    private PersonnelTableauGardeRepository personnelTableauGardeRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private TableauGardeRepository tableauGardeRepository;

    @Autowired
    private JourFerierRepository jourFerierRepository;

    @Autowired
    private DelaiRamadanRepository delaiRamadanRepository;

    private final String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};

    public List<FicheCalcule> ficheCalcules(Trimestre trimestre) {
        List<FicheCalcule> fiches = new ArrayList<>();
        int id = 1;

        //  Map pour regrouper les fiches par service
        Map<Integer, List<FicheCalcule>> fichesParService = new HashMap<>();

        for (Services service : serviceRepository.findAll()) {
            int serviceId = service.getId();
            List<Personnel> personnels = personnelRepository.findByServiceId(serviceId);

            for (Personnel personnel : personnels) {
                
                String t1 = "", t2 = "", t3 = "";
                int nt1 = 1, nt2 = 1, nt3 = 1;
                int[] nVend = {0, 0, 0};
                int[] nNormal = {0, 0, 0};
                int[] nFerier = {0, 0, 0};
                int[] nNuit = {0, 0, 0};
                int[] nNormalRamadan = {0, 0, 0};
                String typeGarde = "";

                
                List<TableauGarde> tableaux = tableauGardeRepository.findByTrimestre(trimestre);

                for (TableauGarde tableau : tableaux) {
                    List<PersonnelTableauGarde> details = personnelTableauGardeRepository.findByTableauId(tableau.getId());

                    for (PersonnelTableauGarde detail : details) {
                        
                        if (detail.getPersonnel().getId() != personnel.getId()) {
                            continue;
                        }

                        int mois = detail.getTableau().getMois();

                        if (mois >= 1 && mois <= 10) {
                            t1 = months[mois - 1];
                            nt1 = mois;

                            t2 = months[mois];
                            nt2 = mois + 1;

                            t3 = (mois < 12) ? months[mois + 1] : "";
                            nt3 = (mois < 12) ? mois + 2 : 0;
                        } else if (mois >= 2 && mois <= 11) {
                            t1 = months[mois - 2];
                            nt1 = mois - 1;

                            t2 = months[mois - 1];
                            nt2 = mois;

                            t3 = (mois < 12) ? months[mois] : "";
                            nt3 = (mois < 12) ? mois + 1 : 0;
                        } else if (mois >= 3 && mois <= 12) {
                            t1 = months[mois - 3];
                            nt1 = mois - 2;

                            t2 = months[mois - 2];
                            nt2 = mois - 1;

                            t3 = months[mois - 1];
                            nt3 = mois;
                        }

                        LocalDate dateGarde = detail.getDateGardeAsLocalDate();
                        typeGarde = detail.getTableau().getTypeGarde();
                        int etatGarde = detail.getEtatGarde();

                        // Calcul..
                        if (etatGarde == 0) {
                            if (isHoliday(dateGarde) || dateGarde.getDayOfWeek() == DayOfWeek.SATURDAY || dateGarde.getDayOfWeek() == DayOfWeek.SUNDAY) {
                                nFerier[(mois - 1) % 3]++;
                            }

                            if (dateGarde.getDayOfWeek() == DayOfWeek.FRIDAY) {
                                nVend[(mois - 1) % 3]++;
                            }

                            if (!isHoliday(dateGarde) && !isRamadanDay(dateGarde) && dateGarde.getDayOfWeek().getValue() > 1 && dateGarde.getDayOfWeek().getValue() < 6) {
                                nNormal[(mois - 1) % 3]++;
                            }

                            if (isRamadanDay(dateGarde) && !isHoliday(dateGarde) && dateGarde.getDayOfWeek().getValue() > 1 && dateGarde.getDayOfWeek().getValue() < 6) {
                                nNormalRamadan[(mois - 1) % 3]++;
                            }
                        } else {
                            nNuit[(mois - 1) % 3]++;
                        }
                    }
                }

           
                FicheCalcule fiche = new FicheCalcule();
                fiche.setId(id++);
                fiche.setTauxFrais(personnel.getTaux());
                fiche.setTaux(personnel.getFonction().getTaux());
                fiche.setT1(t1);
                fiche.setT2(t2);
                fiche.setT3(t3);
                fiche.setT1Num(nt1);
                fiche.setT2Num(nt2);
                fiche.setT3Num(nt3);
                fiche.setNumTrimestre(trimestre.getNumeroTrimestre());
                fiche.setImt(personnel.getImt());
                fiche.setCin(personnel.getCin());
                fiche.setAnnee(trimestre.getAnnee());
                fiche.setServiceId(serviceId);
                fiche.setNomService(service.getTitre());
                fiche.setTypeGarde(typeGarde);
                fiche.setNomComplet(personnel.getNomComplet());
                fiche.setT1NbrFerier(nFerier[0]);
                fiche.setT1NbrNormal(nNormal[0]);
                fiche.setT1NbrNuit(nNuit[0]);
                fiche.setT1NbrVend(nVend[0]);
                fiche.setT1NbrNormalRamadan(nNormalRamadan[0]);
                fiche.setT2NbrFerier(nFerier[1]);
                fiche.setT2NbrNormal(nNormal[1]);
                fiche.setT2NbrNuit(nNuit[1]);
                fiche.setT2NbrVend(nVend[1]);
                fiche.setT2NbrNormalRamadan(nNormalRamadan[1]);
                fiche.setT3NbrFerier(nFerier[2]);
                fiche.setT3NbrNormal(nNormal[2]);
                fiche.setT3NbrNuit(nNuit[2]);
                fiche.setT3NbrVend(nVend[2]);
                fiche.setT3NbrNormalRamadan(nNormalRamadan[2]);

                // ici on Ajoute la fiche à la liste du service dans la map
                fichesParService.computeIfAbsent(serviceId, k -> new ArrayList<>()).add(fiche);
            }
        }

        // ici on Convertit la Map en une liste de fiches groupées par service
        fiches.addAll(fichesParService.values().stream().flatMap(List::stream).toList());

        return fiches;
    }

    private boolean isHoliday(LocalDate date) {
        return jourFerierRepository.findAll().stream().anyMatch(jourFerier -> jourFerier.getDate().equals(date));
    }

    private boolean isRamadanDay(LocalDate date) {
        return delaiRamadanRepository.findAll().stream().anyMatch(delaiRamadan -> date.isAfter(delaiRamadan.getDateDebut().minusDays(1)) && date.isBefore(delaiRamadan.getDateFin().plusDays(1)));
    }
}
