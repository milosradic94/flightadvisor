package com.losmilos.flightadvisor.model.persistance;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "airport")
@Getter @Setter
public class AirportEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private CityEntity city;

    @Column
    private String iata;

    @Column
    private String icao;

    @Column(precision = 10, scale = 7, name = "`lat`")
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7, name = "`long`")
    private BigDecimal longitude;

    @Column
    private Float altitude;

    @Column
    private Float timezone;

    @Column
    private String dst;

    @Column
    private String tz;

    @Column
    private String type;

    @Column
    private String source;
}
