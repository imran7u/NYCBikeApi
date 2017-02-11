package com.nyc.station.api.controller.model;

import java.util.ArrayList;
import java.util.List;

public class CopyOfGetAvailableBikeDockDto extends ResponseDto {
	
	private List<AvailableBikeDockDto> availableBikeDocks = new ArrayList<AvailableBikeDockDto>();

	public List<AvailableBikeDockDto> getAvailableBikeDocks() {
		return availableBikeDocks;
	}

	public void setAvailableBikeDocks(List<AvailableBikeDockDto> availableBikeDocks) {
		this.availableBikeDocks = availableBikeDocks;
	}

	
 
}
