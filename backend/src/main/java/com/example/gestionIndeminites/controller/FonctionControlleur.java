package com.example.gestionIndeminites.controller;

import com.example.gestionIndeminites.entity.Fonction;
import com.example.gestionIndeminites.service.FonctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fonctions")
public class FonctionControlleur {

    private final FonctionService fonctionService;

    @Autowired
    public FonctionControlleur(FonctionService fonctionService) {
        this.fonctionService = fonctionService;
    }
    @CrossOrigin

    @GetMapping
    public ResponseEntity<List<Fonction>> getAllFonctions() {
        List<Fonction> fonctions = fonctionService.getAllFonctions();
        return ResponseEntity.ok(fonctions);
    }
    @CrossOrigin

    @GetMapping("/{id}")
    public ResponseEntity<Fonction> getFonctionById(@PathVariable("id") int id) {
        Optional<Fonction> fonction = fonctionService.getFonctionById(id);
        return fonction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @CrossOrigin

    @PostMapping
    public ResponseEntity<Fonction> createFonction(@RequestBody Fonction fonction) {
        Fonction createdFonction = fonctionService.saveFonction(fonction);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFonction);
    }
    @CrossOrigin

    @PutMapping("/{id}")
    public ResponseEntity<Fonction> updateFonction(@PathVariable("id") int id, @RequestBody Fonction fonction) {
        if (!fonctionService.getFonctionById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        fonction.setId(id); // Assure that the ID in the request body matches the path variable ID
        Fonction updatedFonction = fonctionService.saveFonction(fonction);
        return ResponseEntity.ok(updatedFonction);
    }
    @CrossOrigin

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFonction(@PathVariable("id") int id) {
        if (!fonctionService.getFonctionById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        fonctionService.deleteFonctionById(id);
        return ResponseEntity.noContent().build();
    }
}
