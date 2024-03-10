package com.migros.couriertracking.strategy;

import com.migros.couriertracking.model.GeoLocation;

public class HaversineDistanceCalculationStrategy implements DistanceCalculationStrategy{
    @Override
    public double calculateDistance(GeoLocation location1, GeoLocation location2) {
        // Radius of the Earth in meters
        final double R = 6371000.0;

        // Convert latitude and longitude from degrees to radians
        double lat1 = Math.toRadians(location1.getLatitude());
        double lon1 = Math.toRadians(location1.getLongitude());
        double lat2 = Math.toRadians(location2.getLatitude());
        double lon2 = Math.toRadians(location2.getLongitude());

        // Differences between the latitudes and longitudes
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        // Haversine formula
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }
}
