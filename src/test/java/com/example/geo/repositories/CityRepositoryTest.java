package com.example.geo.repositories;

import com.example.geo.entities.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CityRepositoryTest {

    @Autowired
    CityRepository cityRepository;

    GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    Double lat = 59.938879;
    Double lon = 30.315212;

    @Test
    void selectCityWithPoint() {
        // given
        Point pointToOrderFrom = geometryFactory.createPoint(new Coordinate(lon, lat));
        List<Long> orderedIds = new ArrayList<>(Arrays.asList(2L));
        List<String> orderedNames =  new ArrayList<>(Arrays.asList("Санкт-Петербург"));

        // when
        List<City> result = cityRepository.selectCityWithPoint(pointToOrderFrom);
        List<Long> actualIds = result.stream().map(City::getId).collect(Collectors.toList());
        List<String> actualNames = result.stream().map(City::getName).collect(Collectors.toList());

        // then
        assertEquals(orderedIds, actualIds);
        assertEquals(orderedNames, actualNames);
    }

}