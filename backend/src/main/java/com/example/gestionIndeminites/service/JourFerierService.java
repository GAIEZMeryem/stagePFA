package com.example.gestionIndeminites.service;

import com.example.gestionIndeminites.entity.JourFerier;
import com.example.gestionIndeminites.repository.JourFerierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JourFerierService {

    private final JourFerierRepository jourFerierRepository;

    @Autowired
    public JourFerierService(JourFerierRepository jourFerierRepository) {
        this.jourFerierRepository = jourFerierRepository;
    }

    public List<JourFerier> getAllJourFeriers() {
        return jourFerierRepository.findAll();
    }

    public Optional<JourFerier> getJourFerierById(int id) {
        return jourFerierRepository.findById(id);
    }

    public JourFerier saveJourFerier(JourFerier jourFerier) {
        return jourFerierRepository.save(jourFerier);
    }

    public void deleteJourFerierById(int id) {
        jourFerierRepository.deleteById(id);
    }
   
}
