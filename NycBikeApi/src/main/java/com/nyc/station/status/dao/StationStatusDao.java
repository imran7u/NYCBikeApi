package com.nyc.station.status.dao;

import java.util.Date;

import com.nyc.station.api.controller.model.BikeStats;
import com.nyc.station.entity.dao.DaoException;
import com.nyc.station.entity.dao.EntityDao;
import com.nyc.station.status.model.AvailableBikesAndDocks;
import com.nyc.station.status.model.StationStatus;

public interface StationStatusDao extends EntityDao<StationStatus> {
	
	/**
	 * 
	 * @param stationId
	 * @return
	 * @throws DaoException
	 */
	public AvailableBikesAndDocks getGlobalAvailableBikeDocks(String stationId) throws DaoException;

	
	/**
	 * 
	 * @param Date, Date
	 * @return
	 * @throws DaoException
	 */
	public BikeStats getMonthlyStats(Date start, Date end) throws DaoException;

	
	/**
	 * 
	 * @param Date, Date
	 * @return
	 * @throws DaoException
	 */
	public String getPopularStation(Date start, Date end) throws DaoException;
	
}
