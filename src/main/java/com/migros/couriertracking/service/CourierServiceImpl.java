package com.migros.couriertracking.service;

import com.migros.couriertracking.dto.CourierLocationDto;
import com.migros.couriertracking.model.GeoLocation;
import com.migros.couriertracking.model.Store;
import com.migros.couriertracking.observer.CourierObserver;
import com.migros.couriertracking.strategy.DistanceCalculationStrategy;
import com.migros.couriertracking.strategy.HaversineDistanceCalculationStrategy;
import com.migros.couriertracking.util.StoreLoader;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourierServiceImpl implements CourierService{

    private final StoreLoader storeLoader;
    private final Map<String, List<GeoLocation>> courierMovements; // Map to store movements of each courier
    private final List<CourierObserver> observers;
    private final DistanceCalculationStrategy distanceCalculationStrategy;
    private final Map<String, Map<String, LocalDateTime>> entranceTimeMap = new HashMap<>(); // Map to store entrance times for each courier and store


    public CourierServiceImpl(StoreLoader storeLoader, Map<String, List<GeoLocation>> courierMovements, List<CourierObserver> observers) {
        this.storeLoader = storeLoader;
        this.courierMovements = courierMovements;
        this.observers = observers;
        this.distanceCalculationStrategy = new HaversineDistanceCalculationStrategy();
    }

    // Method to add a new location update for a courier
    public void addLocationUpdate(CourierLocationDto courierLocationDto) {
        if (!courierMovements.containsKey(courierLocationDto.getCourierId())) {
            courierMovements.put(courierLocationDto.getCourierId(), new ArrayList<>());
        }
        GeoLocation LastGeoLocation = new GeoLocation(courierLocationDto.getLatitude(),courierLocationDto.getLongitude());
        courierMovements.get(courierLocationDto.getCourierId()).add(LastGeoLocation);
        logEntranceIfNearAnyMigros(LastGeoLocation, courierLocationDto);
    }

    // Method to detect if courier entered a Migros store
    public void logEntranceIfNearAnyMigros(GeoLocation geoLocation, CourierLocationDto courierLocationDto) {
        LocalDateTime currentTime = courierLocationDto.getTime();

        Map<String, LocalDateTime> entranceTimes = entranceTimeMap.computeIfAbsent(courierLocationDto.getCourierId(), k -> new HashMap<>());

        for (Store store : storeLoader.getStores()) {
            GeoLocation currentStoreGeoLocation = new GeoLocation(store.getLat(),store.getLng());
            double distance = distanceCalculationStrategy.calculateDistance(geoLocation, currentStoreGeoLocation);

            if (distance <= 100) {
                LocalDateTime lastEntranceTime = entranceTimes.get(store.getName());
                if (lastEntranceTime == null || lastEntranceTime.isBefore(currentTime.minusMinutes(1))) {
                    notifyObservers(courierLocationDto.getCourierId(), store.getName(), currentTime);
                    entranceTimes.put(store.getName(), currentTime);
                }
            }
        }
    }

    private void notifyObservers(String courierId, String storeName, LocalDateTime entranceTime) {
        for (CourierObserver observer : observers) {
            observer.update(courierId, storeName, entranceTime);
        }
    }

    // Method to calculate total travel distance for a courier
    public Double getTotalTravelDistance(String courierId) {
        if (courierMovements == null || courierMovements.isEmpty()) {
            return 0d;
        }

        List<GeoLocation> locations = courierMovements.get(courierId);
        if (locations == null || locations.isEmpty() || locations.size() == 1) {
            return 0d;
        }

        double totalDistance = 0.0;
        for (int i = 1; i < locations.size(); i++) {
            GeoLocation prevLocation = locations.get(i - 1);
            GeoLocation currentLocation = locations.get(i);
            totalDistance += distanceCalculationStrategy.calculateDistance(prevLocation, currentLocation);
        }
        return totalDistance;
    }

    public Map<String, List<GeoLocation>> getCourierMovements() {
        return courierMovements;
    }
}
