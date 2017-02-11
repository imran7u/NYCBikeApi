package com.nyc.station.api.controller.model;


public class GetAvailableBikeDockDto extends ResponseDto {
	
	private AvailableBikeDockDto availableBikeDocks = new AvailableBikeDockDto();

	public AvailableBikeDockDto getAvailableBikeDocks() {
		return availableBikeDocks;
	}

	public void setAvailableBikeDocks(AvailableBikeDockDto availableBikeDocks) {
		this.availableBikeDocks = availableBikeDocks;
	}

	
 
}
