package com.VoyageConnect.AgenceDeVoyage.service;

import com.VoyageConnect.AgenceDeVoyage.entity.Flight;
import com.VoyageConnect.AgenceDeVoyage.entity.Offer;
import com.VoyageConnect.AgenceDeVoyage.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.VoyageConnect.AgenceDeVoyage.Dtos.FlightDTO;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private OfferService offerService;
    

    public FlightDTO mapToFlightDTO(Flight flight) {
        return new FlightDTO(
            flight.getId(),
            flight.getAirline(),
            flight.getDeparture(),
            flight.getDestination(),
            flight.getDepartureDate(),
            flight.getReturnDate(),
            flight.getPrice(),
            flight.getOffer() != null ? flight.getOffer().getId() : null
        );
    }

    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll().stream()
                               .map(this::mapToFlightDTO)
                               .collect(Collectors.toList());
    }
    public List<FlightDTO> getFlightsForOfferAsDTO(Long offerId) {
        return flightRepository.findByOfferId(offerId).stream()
                               .map(this::mapToFlightDTO)
                               .collect(Collectors.toList());
    }

    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }

    public Flight saveFlight(Flight flight) {
        // Save the flight
        Flight savedFlight = flightRepository.save(flight);

        // Ensure the flight has an associated offer and update the offer's flight reference
        Offer offer = savedFlight.getOffer();
        if (offer != null) {
            offerService.updateFlightInOffer(offer.getId(), savedFlight.getId());
        }

        return savedFlight;
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
    public List<Flight> getFlightsForOffer(Long offerId) {
        return flightRepository.findByOfferId(offerId);
    }

}