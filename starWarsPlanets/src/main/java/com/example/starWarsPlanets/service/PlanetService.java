package com.example.starWarsPlanets.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.starWarsPlanets.entity.Planet;
import com.example.starWarsPlanets.repository.PlanetRepository;

import jakarta.transaction.Transactional;

@Service
public class PlanetService {

    @Autowired
    private PlanetRepository planetRepository;

    @Transactional
    public Planet savePlanet(Planet planet) throws Exception {
        if (planet == null){
            throw new Exception("Planet cannot be null");
        } else if ( planetRepository.findByName(planet.getName()).isPresent()) {
            throw new Exception("Planet already exists");
        } else{
            return planetRepository.save(planet);
        }
    }

    public List<Planet> getAllPlanets() {
        return planetRepository.findAll();
    }

    public  Planet getPlanetByName(String name) {
        Optional<Planet> planetOptional = planetRepository.findByName(name);
        if(!planetOptional.isPresent()){
            throw new RuntimeException("Planet not found");
        }
        return planetOptional.get();
    }

    public Planet getPlanetById(Long id) {
        return planetRepository.findById(id).get();
    }

    public void deletePlanetById(Long id) {
        planetRepository.deleteById(id);
    }

}
