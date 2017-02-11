package com.nyc.station.api.controller.model;

import java.util.ArrayList;
import java.util.List;


public class StationsDataResDto extends ResponseDto {
	
	private List<StationData> stationData = new ArrayList<StationData>();

	public List<StationData> getStationData() {
		return stationData;
	}

	public void setStationData(List<StationData> stationData) {
		this.stationData = stationData;
	}
	
 
}
