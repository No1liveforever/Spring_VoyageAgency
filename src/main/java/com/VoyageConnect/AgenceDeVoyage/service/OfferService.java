package com.VoyageConnect.AgenceDeVoyage.service;

import com.VoyageConnect.AgenceDeVoyage.entity.Flight;
import com.VoyageConnect.AgenceDeVoyage.entity.Hotel;
import com.VoyageConnect.AgenceDeVoyage.entity.Offer;
import com.VoyageConnect.AgenceDeVoyage.repository.FlightRepository;
import com.VoyageConnect.AgenceDeVoyage.repository.HotelRepository;
import com.VoyageConnect.AgenceDeVoyage.repository.OfferRepository;
import com.VoyageConnect.AgenceDeVoyage.repository.ReservationRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

	@Autowired
	private OfferRepository offerRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private FlightRepository flightRepository;
	@Autowired
	private HotelRepository hotelRepository;

	public List<Offer> getAllOffers() {
		return offerRepository.findAll();
	}

	public Optional<Offer> getOfferById(Long id) {
		return offerRepository.findById(id);
	}

	public Offer saveOffer(Offer offer) {
		return offerRepository.save(offer);
	}

	public void deleteOffer(Long id) {
		offerRepository.deleteById(id);
	}

	// Check if any offers exist for a specific destination
	public boolean hasOffersForDestination(Long destinationId) {
		return offerRepository.existsByDestinationId(destinationId);
	}

	public List<Offer> searchOffers(String country, Double minPrice, Double maxPrice) {
		if (country != null && minPrice != null && maxPrice != null) {
			return offerRepository.findByDestination_CountryAndOfferPriceBetween(country, minPrice, maxPrice);
		} else if (country != null) {
			return offerRepository.findByDestination_Country(country);
		} else if (minPrice != null && maxPrice != null) {
			return offerRepository.findByOfferPriceBetween(minPrice, maxPrice);
		}
		return offerRepository.findAll();
	}

	// Retrieve flights for an offer
	public List<Flight> getFlightsForOffer(Long offerId) {
		return flightRepository.findByOfferId(offerId);
	}

	// Retrieve hotels for an offer
	public List<Hotel> getHotelsForOffer(Long offerId) {
		return hotelRepository.findByOfferId(offerId);
	}

	// Vérification de disponibilité (à implémenter selon vos besoins spécifiques)
	public boolean checkAvailability(Long offerId) {
		Offer offer = offerRepository.findById(offerId)
				.orElseThrow(() -> new EntityNotFoundException("Offer not found"));

		// Exemple simple : limiter à 10 réservations par offre
		long reservationCount = reservationRepository.countByOfferId(offerId);
		return reservationCount < 10;
	}
}
