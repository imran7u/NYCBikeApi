package com.nyc.station.entity.service;

import java.io.Serializable;
import java.util.List;

import com.nyc.station.entity.dao.DaoException;
import com.nyc.station.entity.dao.GenericHibernateDao;
import com.nyc.station.entity.model.Entity;
import com.nyc.station.service.exception.ServiceException;
 
public class GenericEntityService implements EntityService {
	private GenericHibernateDao _genericHibernateDao;
	
	@Override
	public <E extends Entity> E find(Class<E> entityType, Serializable id) throws ServiceException {
		E entity = null;
		try {
			entity = _genericHibernateDao.find(entityType, id);
			return entity;
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public <E extends Entity> E update(E entity) throws ServiceException {
		//TODO: Security checks
		try {
			return _genericHibernateDao.update(entity);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public <E extends Entity> List<E> find(Class<E> entityType, int start, int maximum) throws ServiceException {
		List<E> result = null;
		//TODO: Security checks
		try {
			result = _genericHibernateDao.getAll(entityType, start, maximum);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return result;
	}
	
	@Override
	public <E extends Entity> void delete(E entity) throws ServiceException {
		try {
			_genericHibernateDao.delete(entity);
		} catch (DaoException e) {
			throw new ServiceException("Cannot delete entity", e);
		}
	}

	public void setGenericHibernateDao(GenericHibernateDao genericHibernateDao) {
		_genericHibernateDao = genericHibernateDao;
	}
 
	@Override
	public <E extends Entity> void detach(E entity) {
		_genericHibernateDao.detach(entity);
	}

	
}
