package com.VoyageConnect.AgenceDeVoyage.repository;

import com.VoyageConnect.AgenceDeVoyage.entity.Flight;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
	
    List<Flight> findByOfferId(Long offerId); // Custom query to find flights by offer ID
	
}