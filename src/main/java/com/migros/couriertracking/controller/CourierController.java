package com.migros.couriertracking.controller;

import com.migros.couriertracking.dto.CourierLocationDto;
import com.migros.couriertracking.service.CourierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/couriers")
public class CourierController {

    private final CourierService courierService;

    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @PostMapping("/location")
    public ResponseEntity<String> receiveCourierLocation(@RequestBody CourierLocationDto courierLocationDto) {
        courierService.addLocationUpdate(courierLocationDto);
        return ResponseEntity.ok("Courier location received successfully");
    }

    @GetMapping("/total-distance")
    public ResponseEntity<Double> receiveCourierTotalDistance(@RequestParam String courierId) {
        return ResponseEntity.ok(courierService.getTotalTravelDistance(courierId));
    }
}
