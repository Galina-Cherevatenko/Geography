package com.example.geo.dto;

import com.example.geo.entities.ShopProjection;
import lombok.Data;

@Data
public class ShopDTO {
    private String name;
    private String address;
    private String workingHours;
    private Double distance;


    public ShopDTO (ShopProjection shop) {
        this.name = shop.getName();
        this.address = shop.getAddress();
        this.workingHours = shop.getWorkingHours();
        this.distance = shop.getDistance();
    }

}
