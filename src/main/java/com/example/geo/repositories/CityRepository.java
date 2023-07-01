package com.example.geo.repositories;

import com.example.geo.entities.City;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query(
            value = "select * from city c " + "where st_covers(c.coordinates, :point) = true",
            nativeQuery = true
    )
    List<City> selectCityWithPoint(@Param("point") Point point);
}
