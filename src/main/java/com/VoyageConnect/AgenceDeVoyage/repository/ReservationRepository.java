package com.VoyageConnect.AgenceDeVoyage.repository;

import com.VoyageConnect.AgenceDeVoyage.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByOfferId(Long offerId);  // Custom method to check if reservations exist for an offer
}
