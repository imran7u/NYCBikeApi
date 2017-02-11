package com.nyc.station.status.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nyc.station.api.controller.model.BikeStats;
import com.nyc.station.entity.dao.DaoException;
import com.nyc.station.entity.dao.NativeHibernateDao;
import com.nyc.station.status.model.AvailableBikesAndDocks;
import com.nyc.station.status.model.StationStatus;
 

public class HibernateStationStatusDao extends NativeHibernateDao<StationStatus> implements StationStatusDao {
	private final Logger _log = LoggerFactory.getLogger(HibernateStationStatusDao.class);

	@Override
	public BikeStats getMonthlyStats(Date start, Date end) throws DaoException {
		BikeStats entityList = new BikeStats();
		EntityManager entityManager = null;
		Query query = null;
		try {
			entityManager = getEntityManager(); 
		     String qlString = "SELECT SUM(ss.num_bikes_available) AS available, SUM(ss.num_bikes_disabled) AS disabled FROM "+StationStatus.class.getSimpleName()+" as ss where ss.last_reported_date >=  :start and ss.last_reported_date<= :end";
		     query = entityManager.createQuery(qlString);
		     query.setParameter("start", start);
		     query.setParameter("end", end);
		     Object[] results = (Object[]) query.getSingleResult();
		     entityList.setBikeRides(((Long) results[0]).intValue());
		     entityList.setDisableBikes(((Long) results[1]).intValue());
		} catch (Exception e) {
			_log.error("", e);
		}
		return entityList;
	}
 
	@Override
	public AvailableBikesAndDocks getGlobalAvailableBikeDocks(String stationId) throws DaoException {
		AvailableBikesAndDocks entityList = new AvailableBikesAndDocks();
		EntityManager entityManager = null;
		try {
			entityManager = getEntityManager(); 
		     String qlString = "SELECT SUM(ss.num_bikes_available) AS bikes, SUM(ss.num_docks_available) AS docks FROM "+StationStatus.class.getSimpleName()+" as ss where ss.station_id like '"+stationId+"'";
		     Query q = entityManager.createQuery(qlString);
		     Object[] results = (Object[]) q.getSingleResult();
		     entityList.setNumBikesAvailable(((Long) results[0]).intValue());
		     entityList.setNumDocksAvailable(((Long) results[1]).intValue());
		} catch (Exception e) {
			_log.error("", e);
		}
		return entityList;
	}

	@Override
	public String getPopularStation(Date start, Date end) throws DaoException {
		String popularStationId = null;
		EntityManager entityManager = null;
		try {
			entityManager = getEntityManager(); 
			Session session = entityManager.unwrap(Session.class);
			StationStatus popularStation = 
				    (StationStatus) session.createCriteria(StationStatus.class)
//				    .add(Expression.between("last_reported_date", start, end))
				    .add(Restrictions.ge("last_reported_date", start)) 
					.add(Restrictions.le("last_reported_date", end)) 
				    .addOrder(Order.desc("num_bikes_available"))
				    .setMaxResults(1)
				    .uniqueResult();
			 
			if (popularStation !=null) {
				popularStationId = popularStation.getStation_id();
			}
		} catch (NoResultException e) {
			return null;
		} catch (HibernateException e) {
			throw new DaoException("Error loading object from database", e);
		}
		return popularStationId;
	}
 
	}
