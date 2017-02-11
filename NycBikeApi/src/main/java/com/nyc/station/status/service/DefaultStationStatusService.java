package com.nyc.station.status.service;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nyc.station.api.controller.model.BikeStats;
import com.nyc.station.entity.dao.DaoException;
import com.nyc.station.service.exception.ServiceException;
import com.nyc.station.status.dao.StationStatusDao;
import com.nyc.station.status.model.AvailableBikesAndDocks;

public class DefaultStationStatusService implements StationStatusService{
	
// 	private final static Logger _logger = LoggerFactory.getLogger(DefaultStationStatusService.class);
 	
 	@Autowired
	private StationStatusDao _stationStatusDao;
	
	 
	@Override
	public AvailableBikesAndDocks getGlobalAvailableBikeDocks()
			throws ServiceException {
		try {
			return _stationStatusDao.getGlobalAvailableBikeDocks("%");
		} catch (DaoException e) {
			throw new ServiceException("Unable to get AvailableBikeDocks", e);
		}
	}

	@Override
	public AvailableBikesAndDocks getStationStats(String stationId)
			throws ServiceException {
		try {
			return _stationStatusDao.getGlobalAvailableBikeDocks(stationId);
		} catch (DaoException e) {
			throw new ServiceException("Unable to get AvailableBikeDocks", e);
		}
	}

	@Override
	public BikeStats getMonthlyStats(int month) throws ServiceException {
		try {
			return _stationStatusDao.getMonthlyStats(getMonthDate(month, true), getMonthDate(month, false));
		} catch (DaoException e) {
			throw new ServiceException("Unable to get AvailableBikeDocks", e);
		}
	}

	@Override
	public String getPopularStation(int month) throws ServiceException {
		try {
			return _stationStatusDao.getPopularStation(getMonthDate(month, true), getMonthDate(month, false));
		} catch (DaoException e) {
			throw new ServiceException("Unable to get AvailableBikeDocks", e);
		}
	}
 

	public void setStationStatusDao(StationStatusDao stationStatusDao) {
		_stationStatusDao = stationStatusDao;
	}
 
		private Date getMonthDate(int month, boolean startDate){
			Calendar c = Calendar.getInstance();  
			c.set(Calendar.MONTH, month-1);
			if (startDate) {
				c.set(Calendar.DAY_OF_MONTH, 1);
			}else{
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			}
			return c.getTime();	
		}
}
