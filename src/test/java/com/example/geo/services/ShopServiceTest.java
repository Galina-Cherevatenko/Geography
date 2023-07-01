package com.example.geo.services;

import com.example.geo.entities.Shop;
import com.example.geo.entities.ShopProjection;
import com.example.geo.repositories.ShopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    @InjectMocks
    private ShopService shopService;

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    private Double lat = 55.7538337;
    private Double lon = 37.6211812;
    private ShopProjection shopProjection1;
    private ShopProjection shopProjection2;
    private ShopProjection shopProjection3;
    private List<ShopProjection> shopProjectionList;
    private Point pointToOrderFrom;


    @BeforeEach
    public void setup(){
        shopProjection1 = new ShopProjection() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "��������� �����";
            }

            @Override
            public String getAddress() {
                return "�. ������, ������� �������";
            }

            @Override
            public String getWorkingHours() {
                return "�� - ��: 9:00 - 21:00 ��, �� - ������� ���";
            }

            @Override
            public Double getDistance() {
                return 0.0;
            }
        };

        shopProjection2 = new ShopProjection() {
            @Override
            public Long getId() {
                return 2L;
            }

            @Override
            public String getName() {
                return "�������������";
            }

            @Override
            public String getAddress() {
                return "�. ����� - ���������, ��������� �������";
            }

            @Override
            public String getWorkingHours() {
                return "�� - ��: 9:00 - 21:00 ��, �� - ������� ���";
            }

            @Override
            public Double getDistance() {
                return 100.0;
            }
        };
        shopProjection3 = new ShopProjection() {
            @Override
            public Long getId() {
                return 3L;
            }

            @Override
            public String getName() {
                return "���� ������";
            }

            @Override
            public String getAddress() {
                return "��������� ���., ��. ������";
            }

            @Override
            public String getWorkingHours() {
                return "�� - ��: 9:00 - 21:00 ��, �� - ������� ���";
            }

            @Override
            public Double getDistance() {
                return 300.0;
            }
        };
        shopProjectionList =  new ArrayList<>(Arrays.asList(shopProjection1,shopProjection2,shopProjection3));
        pointToOrderFrom = geometryFactory.createPoint(new Coordinate(lon, lat));
    }

    @Test
    void getClosestShop() {
        when(shopRepository.findAllShopsOrderedByDistance(pointToOrderFrom)).thenReturn(shopProjectionList);
        ShopProjection expectedShopProjection = shopProjection1;

        ShopProjection result = shopService.getClosestShop(lat, lon);

        assertNotNull(result);
        assertEquals(expectedShopProjection.getName(), result.getName());
        assertEquals(expectedShopProjection.getId(), result.getId());

    }

    @Test
    void getAllShopsOrderedByDistance() {
        when(shopRepository.findAllShopsOrderedByDistance(pointToOrderFrom)).thenReturn(shopProjectionList);

        List<ShopProjection> resultList = shopService.getAllShopsOrderedByDistance(lat, lon);

        assertNotNull(resultList);
        assertEquals(shopProjectionList.size(), resultList.size());
        assertEquals(shopProjectionList.get(0).getId(), resultList.get(0).getId());
        assertEquals(shopProjectionList.get(1).getId(), resultList.get(1).getId());
    }

    @Test
    void getAllShopsInViewPort() {
        Double xMin = 24.9523;
        Double yMin = 60.1693;
        Double xMax = 39.7419;
        Double yMax = 54.629;

        Shop shop1  = new Shop(1L, "��������� �����", "�. ������, ������� �������",
                55.7538337, 37.6211812,"�� - ��: 9:00 - 21:00 ��, �� - ������� ���");
        Shop shop2  = new Shop(2L, "�������������", "�. ����� - ���������, ��������� �������",
                59.938879, 30.315212,"�� - ��: 9:00 - 21:00 ��, �� - ������� ���");
        List<Shop> shops = new ArrayList<>(Arrays.asList(shop1, shop2));
        when(shopRepository.findAllShopsInViewPort(any())).thenReturn(shops);

        List<Shop> resultList = shopService.getAllShopsInViewPort(xMin, yMin, xMax, yMax);

        assertNotNull(resultList);
        assertEquals(shops.size(), resultList.size());
        assertEquals(shops.get(0).getId(), resultList.get(0).getId());
        assertEquals(shops.get(1).getId(), resultList.get(1).getId());
    }
}