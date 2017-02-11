package com.nyc.station.info.dao;

import java.util.List;

import com.nyc.station.entity.dao.DaoException;
import com.nyc.station.entity.dao.EntityDao;
import com.nyc.station.info.model.StationInfo;

public interface StationInfoDao extends EntityDao<StationInfo> {
	
	/**
	 * 
	 * @param streetName
	 * @return StationInfo
	 * @throws DaoException
	 */
	public StationInfo getClosestStreetSation(String streetName) throws DaoException;
	
	
	/**
	 * 
	 * @param longitude
	 * @param latitude
	 * @return
	 * @throws DaoException
	 */
	public StationInfo getClosestSation(String longitude, String latitude) throws DaoException;

	
	/**
	 * 
	 * @param bikes
	 * @return
	 * @throws DaoException
	 */
	public List<StationInfo> getSationWithCapicity(Integer bikes) throws DaoException;
	
 

}
