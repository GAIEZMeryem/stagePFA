package com.example.gestionIndeminites.controller;

import com.example.gestionIndeminites.entity.JourFerier;
import com.example.gestionIndeminites.service.JourFerierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/joursFeries")
public class JourFerierControlleur {

    private final JourFerierService jourFerierService;

    @Autowired
    public JourFerierControlleur(JourFerierService jourFerierService) {
        this.jourFerierService = jourFerierService;
    }
    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<JourFerier>> getAllJourFeriers() {
        List<JourFerier> jourFeriers = jourFerierService.getAllJourFeriers();
        return ResponseEntity.ok(jourFeriers);
    }
    @CrossOrigin

    @GetMapping("/{id}")
    public ResponseEntity<JourFerier> getJourFerierById(@PathVariable("id") int id) {
        Optional<JourFerier> jourFerier = jourFerierService.getJourFerierById(id);
        return jourFerier.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @CrossOrigin

    @PostMapping
    public ResponseEntity<JourFerier> createJourFerier(@RequestBody JourFerier jourFerier) {
        JourFerier createdJourFerier = jourFerierService.saveJourFerier(jourFerier);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJourFerier);
    }
    @CrossOrigin

    @PutMapping("/{id}")
    public ResponseEntity<JourFerier> updateJourFerier(@PathVariable("id") int id, @RequestBody JourFerier jourFerier) {
        if (!jourFerierService.getJourFerierById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        jourFerier.setId(id); // Assure that the ID in the request body matches the path variable ID
        JourFerier updatedJourFerier = jourFerierService.saveJourFerier(jourFerier);
        return ResponseEntity.ok(updatedJourFerier);
    }
    @CrossOrigin

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJourFerier(@PathVariable("id") int id) {
        if (!jourFerierService.getJourFerierById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        jourFerierService.deleteJourFerierById(id);
        return ResponseEntity.noContent().build();
    }
}
