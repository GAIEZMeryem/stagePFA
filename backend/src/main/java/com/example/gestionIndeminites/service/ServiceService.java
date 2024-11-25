package com.example.gestionIndeminites.service;


import com.example.gestionIndeminites.entity.Services;
import com.example.gestionIndeminites.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<Services> getAllServices() {
        return serviceRepository.findAll();
    }

    public List<Services> getServicesByTrimestreId(int trimestreId) {
        return serviceRepository.findByTrimestreId(trimestreId);
    }

    public Optional<Services> getServiceById(int id) {
        return serviceRepository.findById(id);
    }

    public Services saveService(Services service) {
        return serviceRepository.save(service);
    }

    public void deleteServiceById(int id) {
        serviceRepository.deleteById(id);
    }
}
