package com.example.starWarsPlanets.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.starWarsPlanets.entity.Planet;
import com.example.starWarsPlanets.repository.PlanetRepository;
import com.example.starWarsPlanets.service.PlanetService;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @Autowired
    private PlanetRepository planetRepository;

    @PostMapping("/save")
    public ResponseEntity<Planet> savePlanet(@RequestBody Planet planet) { 

        Optional<Planet> planetByName = planetRepository.findByName(planet.getName());

        if (planetByName.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); 
        }
        try {
            Planet savedPlanet = planetService.savePlanet(planet);
            return new ResponseEntity<>(savedPlanet, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

  @GetMapping
public ResponseEntity<List<Planet>> getAllPlanets(@RequestParam(required = false) String name) {
    if (name == null) {
        List<Planet> planets = planetService.getAllPlanets();
        return new ResponseEntity<>(planets, HttpStatus.OK);
    } else {
            try {
                Planet planet = planetService.getPlanetByName(name);
                return new ResponseEntity<>(Collections.singletonList(planet), HttpStatus.OK);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
         }
}

    @GetMapping("/{id}")
    public ResponseEntity<Planet> getPlanetById(@PathVariable Long id) {
        if (id==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
        Planet planet = planetService.getPlanetById(id);
        return new ResponseEntity<>(planet, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlanet(@PathVariable Long id) {
        try{
           planetService.deletePlanetById(id);
            return new ResponseEntity<>(HttpStatus.OK); 
        } catch(Exception e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }

}

