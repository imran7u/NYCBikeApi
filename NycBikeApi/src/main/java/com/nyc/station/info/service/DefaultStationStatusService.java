package com.nyc.station.info.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nyc.station.entity.dao.DaoException;
import com.nyc.station.info.dao.StationInfoDao;
import com.nyc.station.info.model.StationInfo;
import com.nyc.station.service.exception.ServiceException;

public class DefaultStationStatusService implements StationInfoService{
	
 	private final static Logger _logger = LoggerFactory.getLogger(DefaultStationStatusService.class);
 	
 	@Autowired
	private StationInfoDao _stationInfoDao;

	@Override
	public StationInfo getClosestSation(String longitude, String latitude) throws ServiceException {
		try {
			return _stationInfoDao.getClosestSation(longitude, latitude);
		} catch (DaoException e) {
			throw new ServiceException("Unable to get StationInfo", e);
		}
	}

	@Override
	public StationInfo getClosestStreetSation(String streetName) throws ServiceException {
		try {
			return _stationInfoDao.getClosestStreetSation(streetName);
		} catch (DaoException e) {
			throw new ServiceException("Unable to get StationInfo", e);
		}
	}

	@Override
	public List<StationInfo> getSationWithCapicity(Integer num_bikes) throws ServiceException {
		try {
			return _stationInfoDao.getSationWithCapicity(num_bikes);
		} catch (DaoException e) {
			throw new ServiceException("Unable to get StationInfo", e);
		}
	}
  
 
}
