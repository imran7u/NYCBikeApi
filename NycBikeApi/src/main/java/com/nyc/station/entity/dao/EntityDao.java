package com.nyc.station.entity.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.nyc.station.entity.model.Entity;

public interface EntityDao<T extends Entity> {
 
	
	public <P extends T> P save(P entity) throws DaoException;
	public void saveAll(Collection<? extends Entity> entities) throws DaoException;
	
	/**
	 * This is just for testing of transactional operations. DO NOT USE in code
	 */
	public void saveAllNoTx(Collection<? extends Entity> entities) throws DaoException;
	
	public void delete(T entity) throws DaoException;
	
	public void deleteAll(Collection<T> entities) throws DaoException;
	
	public T findById(Serializable id) throws DaoException;

	/**
	 * Should not normally be used as it would hang the application 
	 * when retrieving too many records. Use it only when you understand 
	 * what you are doing and all the consequences
	 * @return
	 * @throws DaoException
	 */
	@Deprecated
	public List<T> getAll() throws DaoException;
	/**
	 * @param start first record to get
	 * @param maximum maximum number of records
	 * @return
	 * @throws DaoException
	 */
	public List<T> getAll(Integer start, Integer maximum) throws DaoException;
 	
	public void detach(Entity entity);
	
	public boolean isProxy(Object proxy);

	public <E extends Entity> Class<E> getClassWithoutInitializingProxy(E entity);

}
