package com.example.geo.repositories;

import com.example.geo.entities.Shop;
import com.example.geo.entities.ShopProjection;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    @Query(
            value = "select s.id, " +
                    "s.name, " +
                    "s.address, " +
                    "s.working_hours, " +
                    "round(" +
                    "cast(st_distancesphere(position, :point) as numeric), 2) as distance, " +
                    "s.lat, s.lon from shop s order by distance",
            nativeQuery = true
    )
    List<ShopProjection> findAllShopsOrderedByDistance(@Param ("point") Point point);

    @Query("select shop from Shop shop where within(shop.position, :viewPort)")
    List<Shop> findAllShopsInViewPort(@Param ("viewPort") Polygon viewPort);
}
