package com.example.geo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.locationtech.jts.geom.Polygon;

@Entity
@Data
@AllArgsConstructor
@Table(name = "city")
public class City {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "geometry", nullable = false)
    private Polygon coordinates;

    public City(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public City() {
    }
}
