package com.example.gestionIndeminites.controller;

import com.example.gestionIndeminites.entity.Personnel;
import com.example.gestionIndeminites.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personnels")
public class PersonnelControlleur {

    private final PersonnelService personnelService;

    @Autowired
    public PersonnelControlleur(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Personnel>> getAllPersonnels() {
        List<Personnel> personnels = personnelService.getAllPersonnels();
        return ResponseEntity.ok(personnels);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Personnel> getPersonnelById(@PathVariable("id") int id) {
        Optional<Personnel> personnel = personnelService.getPersonnelById(id);
        return personnel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @CrossOrigin
    @GetMapping("/service/{id}")
    public ResponseEntity<List<Personnel>> findPersonnelByServiceId(@PathVariable("id") int id) {
        List<Personnel> personnels = personnelService.findPersonnelByServiceId(id);
        return ResponseEntity.ok(personnels);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Personnel> createPersonnel(@RequestBody Personnel personnel) {
        Personnel createdPersonnel = personnelService.savePersonnel(personnel);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPersonnel);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Personnel> updatePersonnel(@PathVariable("id") int id, @RequestBody Personnel personnel) {
        if (!personnelService.getPersonnelById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        personnel.setId(id); // Assure that the ID in the request body matches the path variable ID
        Personnel updatedPersonnel = personnelService.savePersonnel(personnel);
        return ResponseEntity.ok(updatedPersonnel);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonnel(@PathVariable("id") int id) {
        if (!personnelService.getPersonnelById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        personnelService.deletePersonnelById(id);
        return ResponseEntity.noContent().build();
    }
    
}
