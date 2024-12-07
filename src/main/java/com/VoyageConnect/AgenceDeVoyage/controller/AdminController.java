package com.VoyageConnect.AgenceDeVoyage.controller;

import com.VoyageConnect.AgenceDeVoyage.entity.Destination;
import com.VoyageConnect.AgenceDeVoyage.entity.Offer;
import com.VoyageConnect.AgenceDeVoyage.entity.Reservation;
import com.VoyageConnect.AgenceDeVoyage.repository.DestinationRepository;
import com.VoyageConnect.AgenceDeVoyage.repository.OfferRepository;
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
    
 // Offer CRUD
    @PostMapping("/offer")
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
        // Ensure destinationId is not null or invalid
        if (offer.getDestination() == null || offer.getDestination().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Destination must be set in the offer
        }

        // Retrieve the destination from the database
        Optional<Destination> destination = destinationService.getDestinationById(offer.getDestination().getId());
        if (!destination.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Destination must exist
        }

        // Set the actual Destination object in the Offer
        offer.setDestination(destination.get());

        // Save the offer and return the response
        Offer savedOffer = offerService.saveOffer(offer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOffer);
    }



    @GetMapping("/offers")
    public List<Offer> getAllOffers() {
        return offerService.getAllOffers();
    }

    @GetMapping("/offer/{id}")
    public ResponseEntity<Optional<Offer>> getOfferById(@PathVariable Long id) {
        Optional<Offer> offer = offerService.getOfferById(id);
        return offer.isPresent()
                ? ResponseEntity.ok(offer)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/offer/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable Long id, @RequestBody Offer offer) {
        // Check if the offer exists
        Optional<Offer> existingOffer = offerService.getOfferById(id);
        if (!existingOffer.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Offer not found
        }

        // Ensure the destination exists and is valid
        if (offer.getDestination() == null || !destinationService.getDestinationById(offer.getDestination().getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Destination must exist
        }

        // Set the offer id to maintain consistency
        offer.setId(id);

        // Set the destination explicitly to ensure it's not null
        Destination destination = offer.getDestination();
        if (destination != null && destination.getId() != null) {
            Optional<Destination> existingDestination = destinationService.getDestinationById(destination.getId());
            if (existingDestination.isPresent()) {
                // Only update the destination if necessary and all required fields are present
                Destination updatedDestination = existingDestination.get();
                if (updatedDestination.getCountry() == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Missing country field
                }
                offer.setDestination(updatedDestination);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Destination not found
            }
        }

        // Save the updated offer and return the response
        Offer savedOffer = offerService.saveOffer(offer);
        return ResponseEntity.ok(savedOffer);
    }

    @DeleteMapping("/offer/{id}")
    public ResponseEntity<String> deleteOffer(@PathVariable Long id) {
        if (reservationService.hasReservationsForOffer(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot delete offer with associated reservations.");
        }
        offerService.deleteOffer(id);
        return ResponseEntity.ok("Offer with ID " + id + " has been deleted.");
    }

    // Reservation CRUD
    @PostMapping("/reservation")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        if (!offerService.getOfferById(reservation.getOffer().getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Offer must exist
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.saveReservation(reservation));
    }

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<Optional<Reservation>> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.isPresent()
                ? ResponseEntity.ok(reservation)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/reservation/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        if (!reservationService.getReservationById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!offerService.getOfferById(reservation.getOffer().getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Offer must exist
        }
        reservation.setId(id);
        return ResponseEntity.ok(reservationService.saveReservation(reservation));
    }

    @DeleteMapping("/reservation/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        if (!reservationService.getReservationById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found.");
        }
        reservationService.deleteReservation(id);
        return ResponseEntity.ok("Reservation with ID " + id + " has been deleted.");
    }


}