package com.VoyageConnect.AgenceDeVoyage.service;

import com.VoyageConnect.AgenceDeVoyage.entity.Offer;
import com.VoyageConnect.AgenceDeVoyage.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

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
}
