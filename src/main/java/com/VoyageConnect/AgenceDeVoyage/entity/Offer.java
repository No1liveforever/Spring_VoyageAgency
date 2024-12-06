package com.VoyageConnect.AgenceDeVoyage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    @Column(nullable = false)
    private String offerDetails;

    @Column(nullable = false)
    private Double offerPrice;

    public Offer(Destination destination, String offerDetails, Double offerPrice) {
        this.destination = destination;
        this.offerDetails = offerDetails;
        this.offerPrice = offerPrice;
    }
}
