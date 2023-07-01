package com.example.geo.services;

import com.example.geo.entities.City;
import com.example.geo.repositories.CityRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final GeometryFactory geometryFactory;
    private final CityRepository cityRepository;

    public CityService(GeometryFactory geometryFactory, CityRepository cityRepository) {
        this.geometryFactory = geometryFactory;
        this.cityRepository = cityRepository;
    }

    public List<City> getCitiesWithShop(Double lat, Double lon) {
        return cityRepository.selectCityWithPoint(geometryFactory.createPoint(new Coordinate(lon, lat)));
    }
}
