package com.example.geo.controllers;


import com.example.geo.entities.City;
import com.example.geo.services.CityService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CityController.class)
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;



    @Test
    void getCitiesWithShop() throws Exception {

        Double lat = 59.938879;
        Double lon = 30.315212;

        List<City> cities =  new ArrayList<>(Arrays.asList(new City(2L, "Санкт-Петербург")));
        when(cityService.getCitiesWithShop(lat, lon)).thenReturn(cities);

        ResultActions result = mockMvc.perform(get("/api/cities/withshop?lat=59.938879&lon=30.315212"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[*].name", Matchers.contains("Санкт-Петербург")));

    }
}