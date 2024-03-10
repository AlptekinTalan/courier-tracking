package com.migros.couriertracking.service;

import com.migros.couriertracking.dto.CourierLocationDto;
import com.migros.couriertracking.model.GeoLocation;
import com.migros.couriertracking.model.Store;
import com.migros.couriertracking.observer.CourierObserver;
import com.migros.couriertracking.strategy.DistanceCalculationStrategy;
import com.migros.couriertracking.util.StoreLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourierServiceImplTests {

    @Mock
    private StoreLoader storeLoader;

    private CourierServiceImpl courierService;

    @Mock
    private DistanceCalculationStrategy distanceCalculationStrategy;

    @Mock
    private CourierObserver courierObserver;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Map<String, List<GeoLocation>> courierMovements = new HashMap<>();
        List<CourierObserver> observers = new ArrayList<>();
        observers.add(courierObserver);
        courierService = new CourierServiceImpl(storeLoader, courierMovements, observers);
    }

    @Test
    void testAddLocationUpdate() {
        CourierLocationDto courierLocationDto = new CourierLocationDto();
        courierLocationDto.setCourierId("courier1");
        courierLocationDto.setLatitude(10.0);
        courierLocationDto.setLongitude(20.0);
        courierLocationDto.setTime(LocalDateTime.now());
        courierService.addLocationUpdate(courierLocationDto);

        assertEquals(1, courierService.getCourierMovements().size());
        assertEquals(1, courierService.getCourierMovements().get("courier1").size());

    }

    @Test
    void testLogEntranceIfNearAnyMigros() {
        Store migrosStore = new Store("Migros", 40.0, 30.0);
        when(storeLoader.getStores()).thenReturn(List.of(migrosStore));
        when(distanceCalculationStrategy.calculateDistance(any(), any())).thenReturn(50.0);

        CourierLocationDto courierLocationDto = new CourierLocationDto();
        courierLocationDto.setCourierId("courier1");
        courierLocationDto.setLatitude(40.0);
        courierLocationDto.setLongitude(30.0);
        courierLocationDto.setTime(LocalDateTime.now());

        courierService.addLocationUpdate(courierLocationDto);

        verify(courierObserver, times(1)).update(eq("courier1"), eq("Migros"), any(LocalDateTime.class));
    }

    @Test
    void testGetTotalTravelDistance() {
        String courierId = "courier1";
        Map<String, List<GeoLocation>> courierMovements = new HashMap<>();
        List<GeoLocation> locations = new ArrayList<>();
        locations.add(new GeoLocation(10.0, 20.0));
        locations.add(new GeoLocation(11.0, 21.0));
        courierMovements.put(courierId, locations);

        courierService = new CourierServiceImpl(storeLoader, courierMovements, new ArrayList<>());

        double expectedDistance = 155941.21480117153;
        double actualDistance = courierService.getTotalTravelDistance(courierId);
        assertEquals(expectedDistance, actualDistance, 0.001);
    }
}
