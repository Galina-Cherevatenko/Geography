package com.example.geo.repositories;

import com.example.geo.entities.Shop;
import com.example.geo.entities.ShopProjection;
import com.example.geo.util.Envelope;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShopRepositoryTest {
    @Autowired
    private ShopRepository shopRepository;
    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    private Double lat = 55.7538337;
    private Double lon = 37.6211812;

    @Test
    void findAllShouldReturnRightShops () {
        // given
        int expectedShopsCount = 6;

        // when
        List<Shop> result = shopRepository.findAll();

        // then
        assertEquals(expectedShopsCount, result.size());
    }

    @Test
    void findAllShopsOrderedByDistance() {
            // given
            Point pointToOrderFrom = geometryFactory.createPoint(new Coordinate(lon, lat));
            List<Long> orderedIds = new ArrayList<>(Arrays.asList(1L, 2L, 6L, 3L, 5L, 4L));
            List<String> orderedNames =  new ArrayList<>(Arrays.asList(
                    "Столичный рожок", "Ленинградское", "Мороженое Хинкали", "Сила Сибири", "Золотой слиток",
                    "Ракушка Сахалина"));

            // when
            List<ShopProjection> result = shopRepository.findAllShopsOrderedByDistance(pointToOrderFrom);
            List<Long> actualIds = result.stream().map(ShopProjection::getId).collect(Collectors.toList());
            List<String> actualNames = result.stream().map(ShopProjection::getName).collect(Collectors.toList());

            // then
            assertEquals(orderedIds, actualIds);
            assertEquals(orderedNames, actualNames);
    }


    @Test
    void findAllShopsInViewPort() {
        // given
        List<Long> expectedIds = new ArrayList<>(Arrays.asList(1L, 2L));
        List<String> expectedShopNames =  new ArrayList<>(Arrays.asList("Столичный рожок", "Ленинградское"));
        Envelope env = (geometryFactory, minX, minY, maxX, maxY)->{
            Coordinate[] coords = {new Coordinate(minX, minY),
                    new Coordinate(minX, maxY),
                    new Coordinate(maxX, maxY),
                    new Coordinate(maxX, minY),
                    new Coordinate(minX, minY)};
            LinearRing shell = geometryFactory.createLinearRing(coords);
            return geometryFactory.createPolygon(shell, null);
        };

        // when
        List<Shop> result = shopRepository.findAllShopsInViewPort(
                env.toPolygon(geometryFactory, 24.9523, 60.1693, 39.7419, 54.6295));
        List<Long> actualIds = result.stream().map(Shop::getId).collect(Collectors.toList());
        List<String> actualShopNames = result.stream().map(Shop::getName).collect(Collectors.toList());

        // then
        assertEquals(expectedIds , actualIds);
        assertEquals(expectedShopNames, actualShopNames);
    }

}