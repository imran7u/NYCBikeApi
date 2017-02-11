package com.nyc.station.entity.service;

import java.io.Serializable;
import java.util.List;
import com.nyc.station.entity.model.Entity;
import com.nyc.station.service.exception.ServiceException;

public interface EntityService {
	public <E extends Entity> E find(Class<E> entityType, Serializable id) throws ServiceException;
	public <E extends Entity> List<E> find(Class<E> entityType, int start, int maximum) throws ServiceException;
	public <E extends Entity> void delete(E entity) throws ServiceException;
	public <E extends Entity> void detach(E entity);
	public <E extends Entity> E update(E entity) throws ServiceException;
}
