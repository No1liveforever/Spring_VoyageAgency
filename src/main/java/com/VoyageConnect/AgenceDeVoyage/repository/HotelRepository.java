package com.VoyageConnect.AgenceDeVoyage.repository;

import com.VoyageConnect.AgenceDeVoyage.entity.Hotel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByOfferId(Long offerId); // Custom query to find hotels by offer ID
}
