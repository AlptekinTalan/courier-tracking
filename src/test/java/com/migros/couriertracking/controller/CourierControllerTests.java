package com.migros.couriertracking.controller;

import com.migros.couriertracking.dto.CourierLocationDto;
import com.migros.couriertracking.service.CourierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourierControllerTests {

    @Mock
    private CourierService courierService;

    @InjectMocks
    private CourierController courierController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReceiveCourierLocation() {
        CourierLocationDto courierLocationDto = new CourierLocationDto();
        courierLocationDto.setCourierId("courier001");
        courierLocationDto.setLatitude(40.7128);
        courierLocationDto.setLongitude(-74.0060);
        courierLocationDto.setTime(LocalDateTime.now());

        assertEquals("Courier location received successfully", courierController.receiveCourierLocation(courierLocationDto).getBody());

        verify(courierService).addLocationUpdate(courierLocationDto);
    }

    @Test
    public void testReceiveCourierTotalDistance() {
        String courierId = "courier001";
        double expectedDistance = 100.0;

        when(courierService.getTotalTravelDistance(courierId)).thenReturn(expectedDistance);

        ResponseEntity<Double> responseEntity = courierController.receiveCourierTotalDistance(courierId);

        assertEquals(expectedDistance, responseEntity.getBody(), 0.0);

        verify(courierService).getTotalTravelDistance(courierId);
    }

}
