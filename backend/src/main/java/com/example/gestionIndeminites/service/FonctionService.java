package com.example.gestionIndeminites.service;

import com.example.gestionIndeminites.entity.Fonction;
import com.example.gestionIndeminites.repository.FonctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FonctionService {

    private final FonctionRepository fonctionRepository;

    @Autowired
    public FonctionService(FonctionRepository fonctionRepository) {
        this.fonctionRepository = fonctionRepository;
    }

    public List<Fonction> getAllFonctions() {
        return fonctionRepository.findAll();
    }

    public Optional<Fonction> getFonctionById(int id) {
        return fonctionRepository.findById(id);
    }

    public Fonction saveFonction(Fonction fonction) {
        return fonctionRepository.save(fonction);
    }

    public void deleteFonctionById(int id) {
        fonctionRepository.deleteById(id);
    }
}
