package com.nyc.station.info.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nyc.station.entity.dao.DaoException;
import com.nyc.station.entity.dao.NativeHibernateDao;
import com.nyc.station.info.model.StationInfo;


/**
 * 
 * @author Imran.Saleem
 *
 */
public class HibernateStationInfoDao extends NativeHibernateDao<StationInfo> implements StationInfoDao {
	private final Logger _log = LoggerFactory.getLogger(HibernateStationInfoDao.class);


	@Override
	public StationInfo getClosestStreetSation(String streetName) throws DaoException {
		StationInfo entity = null;
		EntityManager entityManager = null;
		Query query = null;
		try {
			entityManager = getEntityManager();
			query = entityManager.createQuery("from "+StationInfo.class.getSimpleName()+" where name like :streetName");
			query.setParameter("streetName", "%"+streetName+"%");
			List<StationInfo> list = (List<StationInfo>) query.getResultList();
			if (list == null || list.isEmpty()) {
				return null;
			}
			entity =  list.get(0);
		} catch (NoResultException e) {
			return null;
		} catch (HibernateException e) {
			throw new DaoException("Error loading object from database", e);
		}
		return entity;
	}



	@Override
	public List<StationInfo> getSationWithCapicity(Integer bikes)
			throws DaoException {
		List<StationInfo> result = null;
		Query query = null;
		try {
			EntityManager entityManager = getEntityManager();
			query = entityManager.createNativeQuery("select distinct si.* from stationinfo as si" 
					+ " left join stationstatus as ss on ss.station_id=si.station_id"
					+ " where ss.num_bikes_available >=:bikes"
					, StationInfo.class);
			query.setParameter("bikes", bikes);
			result = (List<StationInfo>) query.getResultList();
		} catch (Exception e) {
			_logger.error("Error finding StationInfo ", e);
			result = new ArrayList<StationInfo>();
		}
		return result;
	}


	@Override
	public StationInfo getClosestSation(String longitude, String latitude) throws DaoException {
		StationInfo result = null;
		Query query = null;
		try {
			EntityManager entityManager = getEntityManager();
			query = entityManager.createNativeQuery("select distinct si.* from stationinfo as si" 
					+ " ORDER BY ABS(ABS(si.`lat`- :latitude) + ABS(si.`lon`- :longitude)) ASC LIMIT 1;"
					, StationInfo.class);
			query.setParameter("latitude",latitude);
			query.setParameter("longitude", longitude);
			result = (StationInfo) query.getSingleResult();
		} catch (Exception e) {
			_logger.error("Error finding StationInfo ", e);
			result = new StationInfo();
		}

		return result;

	}


}
