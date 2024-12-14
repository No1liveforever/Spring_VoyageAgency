package com.VoyageConnect.AgenceDeVoyage.controller;

import com.VoyageConnect.AgenceDeVoyage.Dtos.OfferDTO;
import com.VoyageConnect.AgenceDeVoyage.entity.Flight;
import com.VoyageConnect.AgenceDeVoyage.entity.Hotel;
import com.VoyageConnect.AgenceDeVoyage.entity.Offer;
import com.VoyageConnect.AgenceDeVoyage.service.OfferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private OfferService offerService;

    // Endpoint to get all offers
    @GetMapping("/offers")
    public List<OfferDTO> getAllOffers() {
        return offerService.getAllOffers();
    }

    // Endpoint to get offer by ID
    @GetMapping("/offer/{id}")
    public ResponseEntity<OfferDTO> getOfferById(@PathVariable Long id) {
        Optional<Offer> offer = offerService.getOfferById(id);
        if (offer.isPresent()) {
            return ResponseEntity.ok(offerService.mapToOfferDTO(offer.get()));
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint to create a new offer
    @PostMapping("/offer")
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
        Offer savedOffer = offerService.saveOffer(offer);
        return ResponseEntity.status(201).body(savedOffer);
    }

    // Endpoint to delete an offer
    @DeleteMapping("/offer/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to check availability of an offer
    @GetMapping("/offer/{id}/availability")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable Long id) {
        boolean available = offerService.checkAvailability(id);
        return ResponseEntity.ok(available);
    }

    // Endpoint to search offers by country and price range
    @GetMapping("/offers/search")
    public List<Offer> searchOffers(
        @RequestParam(required = false) String country,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice) {
        return offerService.searchOffers(country, minPrice, maxPrice);
    }

    // Endpoint to get all flights for a specific offer
    @GetMapping("/offer/{id}/flights")
    public List<Flight> getFlightsForOffer(@PathVariable Long id) {
        return offerService.getFlightsForOffer(id);
    }

    // Endpoint to get all hotels for a specific offer
    @GetMapping("/offer/{id}/hotels")
    public List<Hotel> getHotelsForOffer(@PathVariable Long id) {
        return offerService.getHotelsForOffer(id);
    }

    // Endpoint to update flight in an offer
    @PutMapping("/offer/{offerId}/flight/{flightId}")
    public ResponseEntity<Void> updateFlightInOffer(@PathVariable Long offerId, @PathVariable Long flightId) {
        offerService.updateFlightInOffer(offerId, flightId);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to update hotel in an offer
    @PutMapping("/offer/{offerId}/hotel/{hotelId}")
    public ResponseEntity<Void> updateHotelInOffer(@PathVariable Long offerId, @PathVariable Long hotelId) {
        offerService.updateHotelInOffer(offerId, hotelId);
        return ResponseEntity.noContent().build();
    }
}
