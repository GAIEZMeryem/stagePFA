package com.example.gestionIndeminites.service;

import com.example.gestionIndeminites.entity.Trimestre;
import com.example.gestionIndeminites.repository.TrimestreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrimestreService {

    private final TrimestreRepository trimestreRepository;

    @Autowired
    public TrimestreService(TrimestreRepository trimestreRepository) {
        this.trimestreRepository = trimestreRepository;
    }

    public List<Trimestre> getAllTrimestres() {
        return trimestreRepository.findAll();
    }

    public Optional<Trimestre> getTrimestreById(int id) {
        return trimestreRepository.findById(id);
    }

    
    public Trimestre saveTrimestre(Trimestre trimestre) {
        return trimestreRepository.save(trimestre);
    }
    public List<Trimestre> getOpenTrimestres() {
        return trimestreRepository.findByEtatTrimestre(0); // Supposons que 0 signifie "ouvert"
    }

    public void deleteTrimestreById(int id) {
        trimestreRepository.deleteById(id);
    }
}
