package com.losmilos.flightadvisor.model.persistance;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "route")
@Getter @Setter
public class RouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String airline;

    @Column(nullable = false)
    private Long airlineId;

    @ManyToOne
    @JoinColumn(name = "source_airport_id", nullable = false)
    private AirportEntity sourceAirport;

    @ManyToOne
    @JoinColumn(name = "destination_airport_id", nullable = false)
    private AirportEntity destinationAirport;

    @Column
    private String codeshare;

    @Column
    private Long stops;

    @Column
    private String equipment;

    @Column(nullable = false)
    private Double price;
}
