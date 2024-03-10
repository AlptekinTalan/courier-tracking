package com.migros.couriertracking.observer;

import java.time.LocalDateTime;

public interface CourierObserver {
    void update(String courierId, String storeName, LocalDateTime entranceTime);
}
