package com.nyc.station.info.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.nyc.station.entity.model.Entity;


@javax.persistence.Entity
@Table(name="StationInfo")
public class StationInfo implements Entity{ 

	@Id
	@Column(name = "station_id", nullable = false)
    private String station_id;
	
	@Temporal(TemporalType.DATE)
	@Column(name="CREATION_DATE", insertable=true, updatable=false)
	private Date _creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFICATION_DATE", insertable=false, updatable=false)	
	private Date _modificationDate;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "short_name")
    private String short_name;
	
	@Column(name = "lat")
    private String lat;
	
	@Column(name = "lon")
    private String lon;
	
	@Column(name = "region_id")
    private String region_id;
	
	@ElementCollection
	@CollectionTable(name="Rental_methods", joinColumns=@JoinColumn(name="id"))
	@Column(name="rental_method")
    private List<String> rental_methods;
	
	@Column(name = "capacity")
    private Integer capacity;
	
	@Column(name = "eightd_has_key_dispenser")
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
 
	@Override
	public Date getCreationDate() {
		return _creationDate;
	}
	@Override
	public Date getModificationDate() {
		return _modificationDate;
	}
    
	
	
}