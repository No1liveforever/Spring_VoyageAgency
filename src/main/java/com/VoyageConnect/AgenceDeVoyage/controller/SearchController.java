package com.VoyageConnect.AgenceDeVoyage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.VoyageConnect.AgenceDeVoyage.entity.Offer;
import com.VoyageConnect.AgenceDeVoyage.service.OfferService;

@RestController
@RequestMapping("/api/search")
public class SearchController {
	@Autowired
	private OfferService offerService;

	@GetMapping
	public ResponseEntity<List<Offer>> searchOffers(@RequestParam(required = false) String country,
			@RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice) {
		List<Offer> offers = offerService.searchOffers(country, minPrice, maxPrice);
		return ResponseEntity.ok(offers);
	}

	@GetMapping("/availability/{offerId}")
	public ResponseEntity<Boolean> checkOfferAvailability(@PathVariable Long offerId) {
		boolean isAvailable = offerService.checkAvailability(offerId);
		return ResponseEntity.ok(isAvailable);
	}
}