package com.nyc.station.api.controller.model;

public class AvailableBikeDockDto {

	private Integer bikeNumber;
	private Integer dockNumber;

	public Integer getBikeNumber() {
		return bikeNumber;
	}

	public void setBikeNumber(Integer bikeNumber) {
		this.bikeNumber = bikeNumber;
	}

	public Integer getDockNumber() {
		return dockNumber;
	}

	public void setDockNumber(Integer dockNumber) {
		this.dockNumber = dockNumber;
	}

	@Override
	public String toString() {
		return "AvailableBikeDockDto [bikeNumber=" + bikeNumber
				+ ", dockNumber=" + dockNumber + "]";
	}
}
