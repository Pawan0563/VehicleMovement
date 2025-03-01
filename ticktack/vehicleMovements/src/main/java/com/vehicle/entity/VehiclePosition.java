package com.vehicle.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class VehiclePosition {
	@Id
	private Long id;
    private double latitude;
    private double longitude;
	public VehiclePosition(double latitude, double longitude) {
		super();
		
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

   
}
