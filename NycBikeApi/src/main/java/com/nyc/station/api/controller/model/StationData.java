package com.nyc.station.api.controller.model;

import java.util.List;


public class StationData { 

	private String station_id; 
    private String name; 
    private String short_name; 
    private String lat; 
    private String lon; 
    private String region_id; 
    private List<String> rental_methods; 
    private Integer capacity; 
    private boolean eightd_has_key_dispenser;
	
    
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShort_name() {
		return short_name;
	}
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getRegion_id() {
		return region_id;
	}
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	public List<String> getRental_methods() {
		return rental_methods;
	}
	public void setRental_methods(List<String> rental_methods) {
		this.rental_methods = rental_methods;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	public boolean isEightd_has_key_dispenser() {
		return eightd_has_key_dispenser;
	}
	public void setEightd_has_key_dispenser(boolean eightd_has_key_dispenser) {
		this.eightd_has_key_dispenser = eightd_has_key_dispenser;
	}
	
}