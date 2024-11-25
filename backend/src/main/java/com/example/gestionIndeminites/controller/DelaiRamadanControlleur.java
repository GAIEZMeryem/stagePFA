package com.example.gestionIndeminites.controller;

import com.example.gestionIndeminites.entity.DelaiRamadan;
import com.example.gestionIndeminites.service.DelaiRamadanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/delais-ramadan")
public class DelaiRamadanControlleur {

    private final DelaiRamadanService delaiRamadanService;

    @Autowired
    public DelaiRamadanControlleur(DelaiRamadanService delaiRamadanService) {
        this.delaiRamadanService = delaiRamadanService;
    }
    @CrossOrigin

    @GetMapping
    public ResponseEntity<List<DelaiRamadan>> getAllDelaiRamadans() {
        List<DelaiRamadan> delaiRamadans = delaiRamadanService.getAllDelaiRamadans();
        return ResponseEntity.ok(delaiRamadans);
    }
    @CrossOrigin

    @GetMapping("/{id}")
    public ResponseEntity<DelaiRamadan> getDelaiRamadanById(@PathVariable("id") int id) {
        Optional<DelaiRamadan> delaiRamadan = delaiRamadanService.getDelaiRamadanById(id);
        return delaiRamadan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @CrossOrigin

    @PostMapping
    public ResponseEntity<DelaiRamadan> createDelaiRamadan(@RequestBody DelaiRamadan delaiRamadan) {
        DelaiRamadan createdDelaiRamadan = delaiRamadanService.saveDelaiRamadan(delaiRamadan);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDelaiRamadan);
    }
    @CrossOrigin

    @PutMapping("/{id}")
    public ResponseEntity<DelaiRamadan> updateDelaiRamadan(@PathVariable("id") int id, @RequestBody DelaiRamadan delaiRamadan) {
        if (!delaiRamadanService.getDelaiRamadanById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        delaiRamadan.setId(id); // Assure that the ID in the request body matches the path variable ID
        DelaiRamadan updatedDelaiRamadan = delaiRamadanService.saveDelaiRamadan(delaiRamadan);
        return ResponseEntity.ok(updatedDelaiRamadan);
    }
    @CrossOrigin

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelaiRamadan(@PathVariable("id") int id) {
        if (!delaiRamadanService.getDelaiRamadanById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        delaiRamadanService.deleteDelaiRamadanById(id);
        return ResponseEntity.noContent().build();
    }
}
