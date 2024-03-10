package com.migros.couriertracking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Store {
    @JsonProperty("name")
    private String name;
    @JsonProperty("lat")
    private double lat;
    @JsonProperty("lng")
    private double lng;

    public Store() {
    }

    public Store(String name, double lat, double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
