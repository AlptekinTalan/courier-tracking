package com.migros.couriertracking.service;

import com.migros.couriertracking.dto.CourierLocationDto;

public interface CourierService {

    void addLocationUpdate(CourierLocationDto courierLocationDto);
    Double getTotalTravelDistance(String courierId);
}
