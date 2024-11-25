package com.example.gestionIndeminites.service;

import com.example.gestionIndeminites.entity.Personnel;
import com.example.gestionIndeminites.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonnelService {

    private final PersonnelRepository personnelRepository;

    @Autowired
    public PersonnelService(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;
    }

    public List<Personnel> getAllPersonnels() {
        return personnelRepository.findAll();
    }

    public Optional<Personnel> getPersonnelById(int id) {
        return personnelRepository.findById(id);
    }

    public Personnel savePersonnel(Personnel personnel) {
        return personnelRepository.save(personnel);
    }

    public void deletePersonnelById(int id) {
        personnelRepository.deleteById(id);
    }
    public List<Personnel> findPersonnelByServiceId(int id){
    	return personnelRepository.findByServiceId(id);
    	
    }
 
    

}
