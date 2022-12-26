package com.losmilos.flightadvisor.model.domain;

import com.losmilos.flightadvisor.model.persistance.AirportEntity;
import com.losmilos.flightadvisor.model.persistance.CityEntity;
import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

import java.math.BigDecimal;

@Setter
public class Airport {

    @CsvBindByPosition(position = 0)
    private Long id;

    @CsvBindByPosition(position = 1)
    private String name;

    @CsvBindByPosition(position = 2)
    private String city;

    @CsvBindByPosition(position = 3)
    private String country;

    @CsvBindByPosition(position = 4)
    private String iata;

    @CsvBindByPosition(position = 5)
    private String icao;

    @CsvBindByPosition(position = 6)
    private String latitude;

    @CsvBindByPosition(position = 7)
    private String longitude;

    @CsvBindByPosition(position = 8)
    private String altitude;

    @CsvBindByPosition(position = 9)
    private String timezone;

    @CsvBindByPosition(position = 10)
    private String dst;

    @CsvBindByPosition(position = 11)
    private String tz;

    @CsvBindByPosition(position = 12)
    private String type;

    @CsvBindByPosition(position = 13)
    private String source;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getIata() {
        return isValid(iata) ? iata : null;
    }

    public String getIcao() {
        return isValid(icao) ? icao : null;
    }

    public BigDecimal getLatitude() {
        return isValid(latitude) ? BigDecimal.valueOf(Double.valueOf(latitude)) : null;
    }

    public BigDecimal getLongitude() {
        return isValid(longitude) ? BigDecimal.valueOf(Double.valueOf(longitude)) : null;
    }

    public Float getAltitude() {
        return isValid(altitude) ? Float.valueOf(altitude) : null;
    }

    public Float getTimezone() {
        return isValid(timezone) ? Float.valueOf(timezone) : null;
    }

    public String getDst() {
        return isValid(dst) ? dst : null;
    }

    public String getTz() {
        return isValid(tz) ? tz : null;
    }

    public String getType() {
        return isValid(type) ? type : null;
    }

    public String getSource() {
        return isValid(source) ? source : null;
    }

    public boolean isValid(String value) {
        return value != null &&
                !value.isBlank() &&
                !value.trim().equals("N");
    }

    public AirportEntity domainToEntity(CityEntity city) {
        return AirportEntity.builder()
                .id(getId())
                .name(getName())
                .city(city)
                .iata(getIata())
                .icao(getIcao())
                .latitude(getLatitude())
                .longitude(getLongitude())
                .altitude(getAltitude())
                .timezone(getTimezone())
                .dst(getDst())
                .tz(getTz())
                .type(getType())
                .source(getSource())
                .build();
    }
}
