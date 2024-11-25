package com.example.gestionIndeminites.controller;

import com.example.gestionIndeminites.entity.Personnel;
import com.example.gestionIndeminites.entity.PersonnelTableauGarde;
import com.example.gestionIndeminites.entity.Services;
import com.example.gestionIndeminites.entity.TableauGarde;
import com.example.gestionIndeminites.entity.Trimestre;
import com.example.gestionIndeminites.service.PersonnelService;
import com.example.gestionIndeminites.service.PersonnelTableauGardeService;
import com.example.gestionIndeminites.service.ServiceService;
import com.example.gestionIndeminites.service.TableauGardeService;
import com.example.gestionIndeminites.service.TrimestreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
@RequestMapping("/api/tableauGardes")
public class TableauGardeControlleur {

    private final TableauGardeService tableauGardeService;
    private final TrimestreService trimestreService;
    private final ServiceService serviceService;
    private final PersonnelTableauGardeService personnelTableauGardeService;
    private final PersonnelService personnelService;

    @Autowired
    
    public TableauGardeControlleur(TableauGardeService tableauGardeService, TrimestreService trimestreService, ServiceService serviceService,PersonnelTableauGardeService personnelTableauGardeService,PersonnelService personnelService) {
        this.tableauGardeService = tableauGardeService;
        this.trimestreService = trimestreService;
        this.serviceService = serviceService;
        this.personnelTableauGardeService = personnelTableauGardeService;
        this.personnelService = personnelService;
    }


    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<TableauGarde>> getAllTableauGardes() {
        List<TableauGarde> tableauGardes = tableauGardeService.findAll();
        return ResponseEntity.ok(tableauGardes);
    }

    @CrossOrigin

    @GetMapping("/{id}")
    public ResponseEntity<TableauGarde> getTableauGardeById(@PathVariable int id) {
        TableauGarde tableauGarde = tableauGardeService.findById(id);
        return tableauGarde != null ? ResponseEntity.ok(tableauGarde) : ResponseEntity.notFound().build();
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<TableauGarde> createTableauGarde(@RequestBody TableauGarde tableauGarde) {
        if (tableauGardeService.existsByAnneeAndTrimestreAndMoisAndServiceId(tableauGarde.getAnnee(), tableauGarde.getTrimestre().getId(), tableauGarde.getMois(), tableauGarde.getService().getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //  409 Conflict si le tableau de garde existe déjà
        }
        TableauGarde createdTableauGarde = tableauGardeService.save(tableauGarde);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTableauGarde);
    }
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<TableauGarde> updateTableauGarde(@PathVariable int id, @RequestBody TableauGarde tableauGarde) {
        if (tableauGardeService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        tableauGarde.setId(id);
        TableauGarde updatedTableauGarde = tableauGardeService.save(tableauGarde);
        return ResponseEntity.ok(updatedTableauGarde);
    }

    @CrossOrigin

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTableauGarde(@PathVariable int id) {
        if (tableauGardeService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        tableauGardeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @CrossOrigin
    @GetMapping("/trimestres")
    public ResponseEntity<List<Trimestre>> getAllTrimestres() {
        List<Trimestre> trimestres = trimestreService.getAllTrimestres();
        return ResponseEntity.ok(trimestres);
    }

    @CrossOrigin
    @GetMapping("/trimestres/open")
    public ResponseEntity<List<Trimestre>> getOpenTrimestres() {
        List<Trimestre> openTrimestres = trimestreService.getOpenTrimestres();
        return ResponseEntity.ok(openTrimestres);
    }

    @CrossOrigin
    @GetMapping("/services")
    public ResponseEntity<List<Services>> getAllServices() {
        List<Services> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
    }
    
}
