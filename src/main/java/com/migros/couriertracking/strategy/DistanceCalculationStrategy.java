package com.migros.couriertracking.strategy;

import com.migros.couriertracking.model.GeoLocation;

public interface DistanceCalculationStrategy {
    double calculateDistance(GeoLocation location1, GeoLocation location2);
}
