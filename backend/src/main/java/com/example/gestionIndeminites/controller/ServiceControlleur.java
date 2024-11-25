package com.example.gestionIndeminites.controller;

import com.example.gestionIndeminites.entity.Services;
import com.example.gestionIndeminites.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
public class ServiceControlleur {

    private final ServiceService serviceService;

    @Autowired
    public ServiceControlleur(ServiceService serviceService) {
        this.serviceService = serviceService;
    }
    @CrossOrigin

    @GetMapping
    public ResponseEntity<List<Services>> getAllServices() {
        List<Services> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
    }
    @CrossOrigin

    @GetMapping("/{id}")
    public ResponseEntity<Services> getServiceById(@PathVariable("id") int id) {
        Optional<Services> service = serviceService.getServiceById(id);
        return service.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @CrossOrigin

    @PostMapping
    public ResponseEntity<Services> createService(@RequestBody Services services) {
        Services createdService = serviceService.saveService(services);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
    }
    @CrossOrigin

    @PutMapping("/{id}")
    public ResponseEntity<Services> updateService(@PathVariable("id") int id, @RequestBody Services services) {
        if (!serviceService.getServiceById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        services.setId(id);
        Services updatedService = serviceService.saveService(services);
        return ResponseEntity.ok(updatedService);
    }
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable("id") int id) {
        if (!serviceService.getServiceById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        serviceService.deleteServiceById(id);
        return ResponseEntity.noContent().build();
    }
}
