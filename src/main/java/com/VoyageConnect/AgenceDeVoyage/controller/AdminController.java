package com.VoyageConnect.AgenceDeVoyage.controller;

import com.VoyageConnect.AgenceDeVoyage.entity.Destination;
import com.VoyageConnect.AgenceDeVoyage.entity.Offer;
import com.VoyageConnect.AgenceDeVoyage.entity.Reservation;
import com.VoyageConnect.AgenceDeVoyage.service.DestinationService;
import com.VoyageConnect.AgenceDeVoyage.service.OfferService;
import com.VoyageConnect.AgenceDeVoyage.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private OfferService offerService;

    @Autowired
    private ReservationService reservationService;

    // Destination CRUD
    @PostMapping("/destination")
    public Destination createDestination(@RequestBody Destination destination) {
        return destinationService.saveDestination(destination);
    }

    @GetMapping("/destinations")
    public List<Destination> getAllDestinations() {
        return destinationService.getAllDestinations();
    }

    @GetMapping("/destination/{id}")
    public Optional<Destination> getDestinationById(@PathVariable Long id) {
        return destinationService.getDestinationById(id);
    }

    @PutMapping("/destination/{id}")
    public Destination updateDestination(@PathVariable Long id, @RequestBody Destination destination) {
        destination.setId(id);
        return destinationService.saveDestination(destination);
    }

    @DeleteMapping("/destination/{id}")
    public ResponseEntity<String> deleteDestination(@PathVariable Long id) {
        if (destinationService.getDestinationById(id).isPresent()) {
            destinationService.deleteDestination(id);
            return ResponseEntity.ok("Destination with ID " + id + " has been deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Destination with ID " + id + " not found.");
        }
    }


    // Similar CRUD endpoints for Offer and Reservation can be created
}