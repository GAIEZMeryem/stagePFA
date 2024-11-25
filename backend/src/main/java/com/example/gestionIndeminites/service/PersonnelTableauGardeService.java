package com.example.gestionIndeminites.service;

import com.example.gestionIndeminites.entity.PersonnelTableauGarde;
import com.example.gestionIndeminites.entity.TableauGarde;
import com.example.gestionIndeminites.repository.PersonnelTableauGardeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonnelTableauGardeService {

    private final PersonnelTableauGardeRepository personnelTableauGardeRepository;

    @Autowired
    public PersonnelTableauGardeService(PersonnelTableauGardeRepository personnelTableauGardeRepository) {
        this.personnelTableauGardeRepository = personnelTableauGardeRepository;
    }

    public List<PersonnelTableauGarde> findAll() {
        return personnelTableauGardeRepository.findAll();
    }

    public PersonnelTableauGarde findById(int id) {
        Optional<PersonnelTableauGarde> personnelTableauGarde = personnelTableauGardeRepository.findById(id);
        return personnelTableauGarde.orElse(null);
    }

    public PersonnelTableauGarde save(PersonnelTableauGarde personnelTableauGarde) {
        return personnelTableauGardeRepository.save(personnelTableauGarde);
    }

    public void deleteById(int id) {
        personnelTableauGardeRepository.deleteById(id);
    }
    public List<PersonnelTableauGarde> findByTableauId(int tableauId) {
        return personnelTableauGardeRepository.findByTableauId(tableauId);
    }
    public boolean existsTableauId(int tableauId) {
        return personnelTableauGardeRepository.existsByTableauId(tableauId);
    }
    
   
}
