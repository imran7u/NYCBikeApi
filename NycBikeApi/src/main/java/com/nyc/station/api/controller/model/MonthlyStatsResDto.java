package com.nyc.station.api.controller.model;


public class MonthlyStatsResDto extends ResponseDto {
	
	private BikeStats bikeStats = null;

	public BikeStats getBikeStats() {
		return bikeStats;
	}

	public void setBikeStats(BikeStats bikeStats) {
		this.bikeStats = bikeStats;
	}
 
	
 
}
