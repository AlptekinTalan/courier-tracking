package com.migros.couriertracking.observer;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StoreEntranceObserver implements CourierObserver {
    @Override
    public void update(String courierId, String storeName, LocalDateTime entranceTime) {
        System.out.println("Courier " + courierId + " entered " + storeName + " at " + entranceTime);
    }
}
