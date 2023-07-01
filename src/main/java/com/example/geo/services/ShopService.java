package com.example.geo.services;

import com.example.geo.entities.Shop;
import com.example.geo.entities.ShopProjection;
import com.example.geo.repositories.ShopRepository;
import com.example.geo.util.Envelope;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {
    private final GeometryFactory geometryFactory;
    private final ShopRepository shopRepository;

    public ShopService(GeometryFactory geometryFactory, ShopRepository shopRepository) {
        this.geometryFactory = geometryFactory;
        this.shopRepository = shopRepository;
    }


    public ShopProjection getClosestShop(Double lat, Double lon) {
        return shopRepository.findAllShopsOrderedByDistance(geometryFactory
                .createPoint(new Coordinate(lon, lat))).stream().findFirst().get();
    }

    public List<ShopProjection> getAllShopsOrderedByDistance(Double lat, Double lon) {
        return shopRepository.findAllShopsOrderedByDistance(geometryFactory.createPoint(new Coordinate(lon, lat)));
    }

    public List<Shop> getAllShopsInViewPort (Double xMin, Double yMin, Double xMax, Double yMax){

        Envelope env = (geometryFactory, minX, minY, maxX, maxY)->{
            Coordinate[] coords = {new Coordinate(minX, minY),
                    new Coordinate(minX, maxY),
                    new Coordinate(maxX, maxY),
                    new Coordinate(maxX, minY),
                    new Coordinate(minX, minY)};
            LinearRing shell = geometryFactory.createLinearRing(coords);
            return geometryFactory.createPolygon(shell, null);
        };

        return shopRepository.findAllShopsInViewPort(env.toPolygon(geometryFactory, xMin, yMin, xMax, yMax));
    }



}
