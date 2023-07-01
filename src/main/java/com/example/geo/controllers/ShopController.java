package com.example.geo.controllers;

import com.example.geo.dto.ShopDTO;
import com.example.geo.entities.Shop;
import com.example.geo.entities.ShopProjection;
import com.example.geo.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController{
    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/distance/closest")
    public ShopDTO getClosestShop (
            @RequestParam(name = "lat", required = true) Double lat,
            @RequestParam(name = "lon", required = true) Double lon) {
        return new ShopDTO(shopService.getClosestShop(lat, lon));
    }

    @GetMapping("/distance/all")
    public List<ShopDTO> getShopsOrderedByDistance(
            @RequestParam(name = "lat", required = true) Double lat,
            @RequestParam(name = "lon", required = true) Double lon) {
        List<ShopProjection> projections = shopService.getAllShopsOrderedByDistance(lat, lon);
        List<ShopDTO> shopDTOList = new ArrayList<>();
        for (ShopProjection projection: projections){
            shopDTOList.add(new ShopDTO(projection));
        }
    return shopDTOList;
    }

    @GetMapping("/viewport")
    public List<Shop> getShopsInViewPort(
            @RequestParam(name = "xMin", required = true) Double xMin,
            @RequestParam(name = "yMin", required = true) Double yMin,
            @RequestParam(name = "xMax", required = true) Double xMax,
            @RequestParam(name = "yMax", required = true) Double yMax) {
        List<Shop> shopList = shopService.getAllShopsInViewPort (xMin, yMin, xMax, yMax);
        return shopList;
    }

}
