package com.example.geo.controllers;


import com.example.geo.entities.Shop;
import com.example.geo.entities.ShopProjection;
import com.example.geo.services.ShopService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShopController.class)
class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    Double lat = 55.7538337;
    Double lon = 37.6211812;

    @Test
    void getClosestShop() throws Exception {

        ShopProjection shopProjection = new ShopProjection() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "Столичный рожок";
            }

            @Override
            public String getAddress() {
                return "г. Москва, Красная площадь";
            }

            @Override
            public String getWorkingHours() {
                return "Пн - Пт: 9:00 - 21:00 Сб, Вс - выхоные дни";
            }

            @Override
            public Double getDistance() {
                return 0.0;
            }
        };

        when(shopService.getClosestShop(lat, lon)).thenReturn(shopProjection);

        ResultActions result = mockMvc.perform(get("/api/shops/distance/closest?lat=55.7538337&lon=37.6211812"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Столичный рожок")));

    }

    @Test
    void getShopsOrderedByDistance() throws Exception {

        ShopProjection shopProjection1 = new ShopProjection() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "Столичный рожок";
            }

            @Override
            public String getAddress() {
                return "г. Москва, Красная площадь";
            }

            @Override
            public String getWorkingHours() {
                return "Пн - Пт: 9:00 - 21:00 Сб, Вс - выхоные дни";
            }

            @Override
            public Double getDistance() {
                return 0.0;
            }
        };

        ShopProjection shopProjection2 = new ShopProjection() {
            @Override
            public Long getId() {
                return 2L;
            }

            @Override
            public String getName() {
                return "Ленинградское";
            }

            @Override
            public String getAddress() {
                return "г. Санкт - Петербург, Дворцовая площадь";
            }

            @Override
            public String getWorkingHours() {
                return "Пн - Пт: 9:00 - 21:00 Сб, Вс - выхоные дни";
            }

            @Override
            public Double getDistance() {
                return 100.0;
            }
        };
        ShopProjection shopProjection3 = new ShopProjection() {
            @Override
            public Long getId() {
                return 3L;
            }

            @Override
            public String getName() {
                return "Сила Сибири";
            }

            @Override
            public String getAddress() {
                return "Иркутская обл., оз. Байкал";
            }

            @Override
            public String getWorkingHours() {
                return "Пн - Пт: 9:00 - 21:00 Сб, Вс - выхоные дни";
            }

            @Override
            public Double getDistance() {
                return 300.0;
            }
        };
        List<ShopProjection> shopProjectionList =  new ArrayList<>(Arrays.asList(
                shopProjection1,shopProjection2,shopProjection3));
        when(shopService.getAllShopsOrderedByDistance(lat, lon)).thenReturn(shopProjectionList);

        ResultActions result = mockMvc.perform(get("/api/shops/distance/all?lat=55.7538337&lon=37.6211812"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[*].name", Matchers.contains("Столичный рожок","Ленинградское", "Сила Сибири")));
    }

    @Test
    void getShopsInViewPort() throws Exception {
        Shop shop1  = new Shop(1L, "Столичный рожок", "г. Москва, Красная площадь",
                55.7538337, 37.6211812,"Пн - Пт: 9:00 - 21:00 Сб, Вс - выхоные дни");
        Shop shop2  = new Shop(2L, "Ленинградское", "г. Санкт - Петербург, Дворцовая площадь",
                59.938879, 30.315212,"Пн - Пт: 9:00 - 21:00 Сб, Вс - выхоные дни");
        List<Shop> shops = new ArrayList<>(Arrays.asList(shop1, shop2));
        when(shopService.getAllShopsInViewPort (24.9523, 60.1693, 39.7419, 54.6295)).thenReturn(shops);

        ResultActions result = mockMvc.perform(get(
                "/api/shops/viewport?xMin=24.9523&yMin=60.1693&xMax=39.7419&yMax=54.6295"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[*].name", Matchers.containsInAnyOrder("Столичный рожок","Ленинградское")));
    }
}