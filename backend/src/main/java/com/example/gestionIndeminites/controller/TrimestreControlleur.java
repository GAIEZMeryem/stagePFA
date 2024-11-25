package com.example.gestionIndeminites.controller;

import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestionIndeminites.DTO.TrimestreDTO;
import com.example.gestionIndeminites.entity.FicheCalcule;
import com.example.gestionIndeminites.entity.Services;
import com.example.gestionIndeminites.entity.Trimestre;
import com.example.gestionIndeminites.service.FicheCalculeService;
import com.example.gestionIndeminites.service.PersonnelTableauGardeService;
import com.example.gestionIndeminites.service.ServiceService;
import com.example.gestionIndeminites.service.TableauGardeService;
import com.example.gestionIndeminites.service.TrimestreService;
import com.example.gestionIndeminites.entity.TableauGarde;

@RestController
@RequestMapping("/api/trimestres")
public class TrimestreControlleur {

    @Autowired
    private TrimestreService trimestreService;

    private final TableauGardeService tableauGardeService;


    @Autowired
    public TrimestreControlleur(TableauGardeService tableauGardeService) {
        this.tableauGardeService = tableauGardeService;
    }
    
    @Autowired
    private ServiceService serviceService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Trimestre>> getAllTrimestres() {
        List<Trimestre> trimestres = trimestreService.getAllTrimestres();
        return ResponseEntity.ok(trimestres);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Trimestre> createTrimestre(@RequestBody TrimestreDTO trimestreDto) {
        Trimestre tri = new Trimestre(trimestreDto.getNumeroTrimestre(), trimestreDto.getAnnee(), 0);
        Trimestre createdTrimestre = trimestreService.saveTrimestre(tri);
        List<Integer> servicesIds = trimestreDto.getServicesIds();
        int[][] mois = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
        };


        for (int serviceId : servicesIds) {
            Services serv = serviceService.getServiceById(serviceId).orElse(null);
            if (serv == null) continue;

            int nt = trimestreDto.getNumeroTrimestre() - 1;
            for (int j = 0; j < mois[nt].length; j++) {
                int moisActuel = mois[nt][j];
                TableauGarde tg = new TableauGarde(0, moisActuel, trimestreDto.getAnnee(), 1, "", "Garde", tri, serv);
                tableauGardeService.save(tg);
                }
            }
        
        return ResponseEntity.ok(createdTrimestre);
    }


    @CrossOrigin
    @GetMapping("/{id}/services")
    public ResponseEntity<List<Services>> getServicesByTrimestre(@PathVariable("id") int id) {
        List<Services> services = serviceService.getServicesByTrimestreId(id);
        return ResponseEntity.ok(services);
    }

    @CrossOrigin
    @GetMapping("/services")
    public ResponseEntity<List<Services>> getAllServices() {
        List<Services> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Trimestre> updateTrimestre(@PathVariable("id") int id, @RequestBody TrimestreDTO trimestreDto) {
        Optional<Trimestre> existingTrimestre = trimestreService.getTrimestreById(id);
        if (existingTrimestre.isPresent()) {
            Trimestre tri = existingTrimestre.get();
            tri.setNumeroTrimestre(trimestreDto.getNumeroTrimestre());
            tri.setAnnee(trimestreDto.getAnnee());
            tri.setEtatTrimestre(1);  // ou toute autre logique pour le statut

            Trimestre updatedTrimestre = trimestreService.saveTrimestre(tri);

            
            return ResponseEntity.ok(updatedTrimestre);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrimestre(@PathVariable("id") int id) {
        Optional<Trimestre> trimestre = trimestreService.getTrimestreById(id);
        if (trimestre.isPresent()) {
            trimestreService.deleteTrimestreById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @CrossOrigin

    @PutMapping("/{id}/close")
    public ResponseEntity<Trimestre> closeTrimestre(@PathVariable("id") int id) {
     
        Trimestre trimestre = trimestreService.getTrimestreById(id).get();
 
        trimestre.setEtatTrimestre(1);  
     
        Trimestre updatedTrimestre = trimestreService.saveTrimestre(trimestre);

        return ResponseEntity.ok(updatedTrimestre);
    }
    @CrossOrigin
    @PutMapping("/{id}/open")
    public ResponseEntity<Trimestre> openTrimestre(@PathVariable("id") int id) {
   
        Trimestre trimestre = trimestreService.getTrimestreById(id).get();
       
        trimestre.setEtatTrimestre(0);  
        Trimestre updatedTrimestre = trimestreService.saveTrimestre(trimestre);

        return ResponseEntity.ok(updatedTrimestre);
    }
    @CrossOrigin
    @GetMapping("/open")
    public ResponseEntity<List<Trimestre>> getOpenTrimestres() {
        List<Trimestre> trimestres = trimestreService.getOpenTrimestres();
        return ResponseEntity.ok(trimestres);
    }
    @Autowired
    private FicheCalculeService ficheCalculeService;

    @CrossOrigin

    @GetMapping("/{id}/fiches")
    public ResponseEntity<List<FicheCalcule>> getFichesForTrimestre(@PathVariable int id) {
        Optional<Trimestre> trimestre = trimestreService.getTrimestreById(id);
        if (trimestre.isPresent()) {
            List<FicheCalcule> fiches = ficheCalculeService.ficheCalcules(trimestre.get());
            return ResponseEntity.ok(fiches);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
