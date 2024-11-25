package com.example.gestionIndeminites.controller;

import com.example.gestionIndeminites.entity.PersonnelTableauGarde;
import com.example.gestionIndeminites.entity.Fonction;
import com.example.gestionIndeminites.entity.Personnel;
import com.example.gestionIndeminites.service.FonctionService;
import com.example.gestionIndeminites.service.PersonnelService;
import com.example.gestionIndeminites.service.PersonnelTableauGardeService;
import com.example.gestionIndeminites.service.TableauGardeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnelTableauGardes")
public class PersonnelTableauGardeControlleur {

    private final PersonnelTableauGardeService personnelTableauGardeService;
    private final PersonnelService personnelService;
    private final FonctionService fonctionService;
   

    @Autowired
    public PersonnelTableauGardeControlleur(PersonnelTableauGardeService personnelTableauGardeService , PersonnelService personnelService, FonctionService fonctionService) {
        this.personnelTableauGardeService = personnelTableauGardeService;
        this.personnelService = personnelService;
        this.fonctionService = fonctionService;
       
    }
    @CrossOrigin

    @GetMapping
    public ResponseEntity<List<PersonnelTableauGarde>> getAllPersonnelTableauGardes() {
        List<PersonnelTableauGarde> personnelTableauGardes = personnelTableauGardeService.findAll();
        return ResponseEntity.ok(personnelTableauGardes);
    }
    @CrossOrigin

    @GetMapping("/{id}")
    public ResponseEntity<PersonnelTableauGarde> getPersonnelTableauGardeById(@PathVariable int id) {
        PersonnelTableauGarde personnelTableauGarde = personnelTableauGardeService.findById(id);
        return personnelTableauGarde != null ? ResponseEntity.ok(personnelTableauGarde) : ResponseEntity.notFound().build();
    }
    @CrossOrigin

    @PostMapping
    public ResponseEntity<PersonnelTableauGarde> createPersonnelTableauGarde(@RequestBody PersonnelTableauGarde personnelTableauGarde) {
        PersonnelTableauGarde createdPersonnelTableauGarde = personnelTableauGardeService.save(personnelTableauGarde);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPersonnelTableauGarde);
    }
    @CrossOrigin

    @PutMapping("/{id}")
    public ResponseEntity<PersonnelTableauGarde> updatePersonnelTableauGarde(@PathVariable int id, @RequestBody PersonnelTableauGarde personnelTableauGarde) {
        if (personnelTableauGardeService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        personnelTableauGarde.setId(id);
        PersonnelTableauGarde updatedPersonnelTableauGarde = personnelTableauGardeService.save(personnelTableauGarde);
        return ResponseEntity.ok(updatedPersonnelTableauGarde);
    }
    @CrossOrigin

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonnelTableauGarde(@PathVariable int id) {
        if (personnelTableauGardeService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        personnelTableauGardeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @CrossOrigin
    
    @GetMapping("/personnel")
    public ResponseEntity<List<Personnel>> getAllPersonnel() {
        List<Personnel> personnels = personnelService.getAllPersonnels();
        return ResponseEntity.ok(personnels);
    }
    
    @CrossOrigin
    @GetMapping("/fonction")
    public ResponseEntity<List<Fonction>> getAllFonction() {
        List<Fonction> fonctions = fonctionService.getAllFonctions();
        return ResponseEntity.ok(fonctions);
    }
    @CrossOrigin
    @GetMapping("/tableauGarde/{tableauId}")
    public ResponseEntity<List<PersonnelTableauGarde>> getPersonnelTableauGardesByTableauGardeId(@PathVariable int tableauId) {
        List<PersonnelTableauGarde> personnelTableauGardes = personnelTableauGardeService.findByTableauId(tableauId);
        return ResponseEntity.ok(personnelTableauGardes);
    }
    @CrossOrigin
    @GetMapping("/exists/{tableauId}")
    public ResponseEntity<Boolean> existsTableauId(@PathVariable int tableauId) {
        boolean exists = personnelTableauGardeService.existsTableauId(tableauId);
        return ResponseEntity.ok(exists);
    }

}
