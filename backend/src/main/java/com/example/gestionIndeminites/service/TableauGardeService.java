package com.example.gestionIndeminites.service;

import com.example.gestionIndeminites.entity.TableauGarde;
import com.example.gestionIndeminites.repository.TableauGardeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableauGardeService {

    private final TableauGardeRepository tableauGardeRepository;

    @Autowired
    public TableauGardeService(TableauGardeRepository tableauGardeRepository) {
        this.tableauGardeRepository = tableauGardeRepository;
    }

    public List<TableauGarde> findAll() {
        return tableauGardeRepository.findAll();
    }

    public TableauGarde findById(int id) {
        Optional<TableauGarde> tableauGarde = tableauGardeRepository.findById(id);
        return tableauGarde.orElse(null);
    }
    public boolean existsByAnneeAndTrimestreAndMoisAndServiceId(int annee, int trimestreId, int mois, int serviceId) {
        return tableauGardeRepository.existsByAnneeAndTrimestre_IdAndMoisAndServiceId(annee, trimestreId, mois, serviceId);
    }

    public TableauGarde save(TableauGarde tableauGarde) {
        return tableauGardeRepository.save(tableauGarde);
    }

    public void deleteById(int id) {
        tableauGardeRepository.deleteById(id);
    }
}
