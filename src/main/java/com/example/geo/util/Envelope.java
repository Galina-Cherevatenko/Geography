package com.example.geo.util;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

@FunctionalInterface
public interface Envelope {
    Polygon toPolygon(GeometryFactory geometryFactory, Double xMin, Double yMin, Double xMax, Double yMax);
}
