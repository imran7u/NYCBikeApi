package com.nyc.station.status.service;

import com.nyc.station.api.controller.model.BikeStats;
import com.nyc.station.service.exception.ServiceException;
import com.nyc.station.status.model.AvailableBikesAndDocks;
 
public interface  StationStatusService {
 
	/**
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public AvailableBikesAndDocks getGlobalAvailableBikeDocks()  throws ServiceException;
	
	/**
	 * 
	 * @param stationId
	 * @return
	 * @throws ServiceException
	 */
	public AvailableBikesAndDocks getStationStats(String stationId)  throws ServiceException;
	
	/**
	 * 
	 * @param month
	 * @return
	 * @throws ServiceException
	 */
	public BikeStats getMonthlyStats(int month)  throws ServiceException;
	
	/**
	 * 
	 * @param month
	 * @return
	 * @throws ServiceException
	 */
	public String getPopularStation(int month)  throws ServiceException;

}
