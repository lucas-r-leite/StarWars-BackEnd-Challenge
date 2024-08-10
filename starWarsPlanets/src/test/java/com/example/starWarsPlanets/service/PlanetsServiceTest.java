package com.example.starWarsPlanets.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.example.starWarsPlanets.entity.Planet;
import com.example.starWarsPlanets.repository.PlanetRepository;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PlanetsServiceTest {

     @Mock
    private PlanetRepository planetRepository;

    @InjectMocks
    private PlanetService planetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePlanet_Success() throws Exception {
        Planet planet = new Planet();
        planet.setName("Earth");

        when(planetRepository.findByName(planet.getName())).thenReturn(Optional.empty());
        when(planetRepository.save(planet)).thenReturn(planet);

        Planet savedPlanet = planetService.savePlanet(planet);

        assertEquals(planet, savedPlanet);
        verify(planetRepository, times(1)).findByName(planet.getName());
        verify(planetRepository, times(1)).save(planet);
    }

    @Test
    void testSavePlanet_ThrowsExceptionWhenPlanetIsNull() {
        Exception exception = assertThrows(Exception.class, () -> {
            planetService.savePlanet(null);
        });

        assertEquals("Planet cannot be null", exception.getMessage());
    }

    @Test
    void testSavePlanet_ThrowsExceptionWhenPlanetAlreadyExists() {
        Planet planet = new Planet();
        planet.setName("Earth");

        when(planetRepository.findByName(planet.getName())).thenReturn(Optional.of(planet));

        Exception exception = assertThrows(Exception.class, () -> {
            planetService.savePlanet(planet);
        });

        assertEquals("Planet already exists", exception.getMessage());
        verify(planetRepository, times(1)).findByName(planet.getName());
        verify(planetRepository, never()).save(any(Planet.class));
    }

    @Test
    void testGetAllPlanets() {
        Planet planet1 = new Planet();
        planet1.setName("Earth");
        
        Planet planet2 = new Planet();
        planet2.setName("Mars");

        when(planetRepository.findAll()).thenReturn(Arrays.asList(planet1, planet2));

        List<Planet> planets = planetService.getAllPlanets();

        assertEquals(2, planets.size());
        verify(planetRepository, times(1)).findAll();
    }

    @Test
    void testGetPlanetByName_Success() {
        Planet planet = new Planet();
        planet.setName("Earth");

        when(planetRepository.findByName(planet.getName())).thenReturn(Optional.of(planet));

        Planet foundPlanet = planetService.getPlanetByName("Earth");

        assertEquals(planet, foundPlanet);
        verify(planetRepository, times(1)).findByName("Earth");
    }

    @Test
    void testGetPlanetByName_PlanetNotFound() {
        when(planetRepository.findByName("Earth")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            planetService.getPlanetByName("Earth");
        });

        assertEquals("Planet not found", exception.getMessage());
        verify(planetRepository, times(1)).findByName("Earth");
    }

    @Test
    void testGetPlanetById_Success() {
        Planet planet = new Planet();
        planet.setId(1L);

        when(planetRepository.findById(1L)).thenReturn(Optional.of(planet));

        Planet foundPlanet = planetService.getPlanetById(1L);

        assertEquals(planet, foundPlanet);
        verify(planetRepository, times(1)).findById(1L);
    }

    @Test
    void testDeletePlanetById() {
        Long id = 1L;

        doNothing().when(planetRepository).deleteById(id);

        planetService.deletePlanetById(id);

        verify(planetRepository, times(1)).deleteById(id);
    }

}
