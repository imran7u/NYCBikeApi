package com.nyc.station.info.service;

import java.util.List;

import com.nyc.station.info.model.StationInfo;
import com.nyc.station.service.exception.ServiceException;
 
public interface  StationInfoService {

	public StationInfo getClosestSation(String longitude, String latitude) throws ServiceException;
	
	public StationInfo getClosestStreetSation(String streetName) throws ServiceException;
	
	public List<StationInfo> getSationWithCapicity(Integer num_bikes) throws ServiceException;
 
}
