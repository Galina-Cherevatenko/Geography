package com.example.geo.dto;

import com.example.geo.entities.City;
import lombok.Data;

@Data
public class CityDTO {
    private Long id;
    private String name;

    public CityDTO(City city) {
        this.id = city.getId();
        this.name = city.getName();
    }

    public CityDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
