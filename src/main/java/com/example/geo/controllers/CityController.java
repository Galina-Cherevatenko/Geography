package com.example.geo.controllers;


import com.example.geo.dto.CityDTO;
import com.example.geo.dto.ShopDTO;
import com.example.geo.entities.City;
import com.example.geo.entities.ShopProjection;
import com.example.geo.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/withshop")
    public List<CityDTO> getCitiesWithShop (
            @RequestParam(name = "lat", required = true) Double lat,
            @RequestParam(name = "lon", required = true) Double lon) {
        List<City> cities = cityService.getCitiesWithShop(lat, lon);
        List<CityDTO> cityDTOList = new ArrayList<>();
        for (City city: cities){
            cityDTOList.add(new CityDTO(city));
        }
        return cityDTOList;
    }
}
