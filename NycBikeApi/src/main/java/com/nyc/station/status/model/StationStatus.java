package com.nyc.station.status.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.nyc.station.entity.model.Entity;


@javax.persistence.Entity
@Table(name="StationStatus")
public class StationStatus implements Entity{
	 
	@Id
	@Column(name = "station_id", nullable = false)
    private String station_id;
	
	@Temporal(TemporalType.DATE)
	@Column(name="CREATION_DATE", insertable=true, updatable=false)
	private Date _creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFICATION_DATE", insertable=false, updatable=false)	
	private Date _modificationDate;
	
	@Column(name = "num_bikes_available")
    private Integer num_bikes_available;
	
	@Column(name = "num_bikes_disabled")
    private Integer num_bikes_disabled;
	
	@Column(name = "num_docks_available")
    private Integer num_docks_available;
	
	@Column(name = "num_docks_disabled")
    private Integer num_docks_disabled;
	
	@Column(name = "is_installed")
    private Integer is_installed;
	
	@Column(name = "is_renting")
    private Integer is_renting;
	
	@Column(name = "is_returning")
    private Integer is_returning;
	
	@Temporal(TemporalType.DATE)
	@Column(name="last_reported")
    private Date last_reported_date;

	@Transient
    private Long last_reported;
	
	@Column(name = "eightd_has_key_dispenser")
    private boolean eightd_has_key_dispenser;
     
	public String getStation_id() {
		return station_id;
	}

	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}

	public Integer getNum_bikes_available() {
		return num_bikes_available;
	}

	public void setNum_bikes_available(Integer num_bikes_available) {
		this.num_bikes_available = num_bikes_available;
	}

	public Integer getNum_bikes_disabled() {
		return num_bikes_disabled;
	}

	public void setNum_bikes_disabled(Integer num_bikes_disabled) {
		this.num_bikes_disabled = num_bikes_disabled;
	}

	public Integer getNum_docks_available() {
		return num_docks_available;
	}

	public void setNum_docks_available(Integer num_docks_available) {
		this.num_docks_available = num_docks_available;
	}

	public Integer getNum_docks_disabled() {
		return num_docks_disabled;
	}

	public void setNum_docks_disabled(Integer num_docks_disabled) {
		this.num_docks_disabled = num_docks_disabled;
	}

	public Integer getIs_installed() {
		return is_installed;
	}

	public void setIs_installed(Integer is_installed) {
		this.is_installed = is_installed;
	}

	public Integer getIs_renting() {
		return is_renting;
	}

	public void setIs_renting(Integer is_renting) {
		this.is_renting = is_renting;
	}

	public Integer getIs_returning() {
		return is_returning;
	}

	public void setIs_returning(Integer is_returning) {
		this.is_returning = is_returning;
	}
	
	public Date getLast_reported_date() {
		return last_reported_date;
	}

	public void setLast_reported_date(Date last_reported_date) {
		this.last_reported_date = last_reported_date;
	}
	
	public Long getLast_reported() {
		return last_reported;
	}
 
	public void setLast_reported(Long last_reported) {
		this.last_reported = last_reported;
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