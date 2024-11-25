package com.example.gestionIndeminites.service;

import com.example.gestionIndeminites.entity.DelaiRamadan;
import com.example.gestionIndeminites.repository.DelaiRamadanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DelaiRamadanService {

    private final DelaiRamadanRepository delaiRamadanRepository;

    @Autowired
    public DelaiRamadanService(DelaiRamadanRepository delaiRamadanRepository) {
        this.delaiRamadanRepository = delaiRamadanRepository;
    }

    public List<DelaiRamadan> getAllDelaiRamadans() {
        return delaiRamadanRepository.findAll();
    }

    public Optional<DelaiRamadan> getDelaiRamadanById(int id) {
        return delaiRamadanRepository.findById(id);
    }

    public DelaiRamadan saveDelaiRamadan(DelaiRamadan delaiRamadan) {
        return delaiRamadanRepository.save(delaiRamadan);
    }

    public void deleteDelaiRamadanById(int id) {
        delaiRamadanRepository.deleteById(id);
    }
}
