package com.vehicle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.entity.VehiclePosition;
import com.vehicle.service.VehicleService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/api/vehicle/current")
    public VehiclePosition getCurrentPosition() {
        return vehicleService.getCurrentPosition();
    }

    @GetMapping("/api/vehicle/route")
    public List<VehiclePosition> getRoute() {
        return vehicleService.getRoute();
    }
}
