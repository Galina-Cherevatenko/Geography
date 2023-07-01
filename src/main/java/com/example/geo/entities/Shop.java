package com.example.geo.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

@Data
@Entity
@Table(name="shop")
public class Shop {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lon;

    private String working_hours;

    @Column(columnDefinition = "geometry", nullable = false)
    private Point position;

    public Shop(Long id, String name, String address, Double lat, Double lon, String working_hours) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.working_hours = working_hours;
    }

    public Shop() {
    }
}
