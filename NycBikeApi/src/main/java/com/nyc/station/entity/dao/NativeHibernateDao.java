package com.nyc.station.entity.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nyc.station.entity.model.Entity;
 
@Repository
public abstract class NativeHibernateDao<T extends Entity> implements EntityDao<T> {

	protected static final Logger _logger = LoggerFactory.getLogger(NativeHibernateDao.class);
	private final Class<T> _entityType;

	
	@PersistenceUnit(unitName="emf")
	private EntityManagerFactory emf;
	protected final static String TARGET_ENTITY_HQL_ALIAS = "obj";
	
	@SuppressWarnings("unchecked")
	public NativeHibernateDao() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		_entityType =  (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}
	
	 
	
	@Override
	public void saveAll(Collection<? extends Entity> entities) throws DaoException {
		EntityTransaction tx = null;
		EntityManager entityManager = null;
		
        try {
        	entityManager = getEntityManager();
	        tx = entityManager.getTransaction();
	        tx.begin();
	        for (Entity entity : entities) {
	        	entity = entityManager.merge(entity);	
	        }
	        entityManager.flush();
	        tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) {
				tx.rollback();
			}
			throw new DaoException("Error saving entities of type "+_entityType.getCanonicalName(), e);
		} catch (javax.persistence.PersistenceException e) {
			if (tx!=null) {
				tx.rollback();
			}
			throw new DaoException("Error saving entities of type "+_entityType.getCanonicalName(), e);
		}  finally {
        }
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveAllNoTx(Collection<? extends Entity> entities) throws DaoException {
		EntityManager entityManager = null;
		
        try {
        	entityManager = getEntityManager();
	        for (Entity entity : entities) {
	        	entityManager.merge(entity);
	        }
		} catch (HibernateException e) {
			throw new DaoException("Error saving entities of type "+_entityType.getCanonicalName(), e);
		} catch (javax.persistence.PersistenceException e) {
			throw new DaoException("Error saving entities of type "+_entityType.getCanonicalName(), e);
		}  finally {
        }
	}

	@Override
	public <P extends T> P save(P entity) throws DaoException {
		EntityTransaction tx = null;
		EntityManager entityManager = null;
		P mergedEntity = null;
        try {
        	entityManager = getEntityManager();
	        tx = entityManager.getTransaction();
	        tx.begin();
	        mergedEntity = entityManager.merge(entity);
	        entityManager.flush();
	        tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) {
				tx.rollback();
			}
			throw new DaoException("Error saving entity of type "+(entity==null?"null":entity.getClass()), e);
		} catch (javax.persistence.PersistenceException e) {
			if (tx!=null) {
				tx.rollback();
			}
			throw new DaoException("Error saving entity of type "+(entity==null?"null":entity.getClass()), e);
		}  finally {
        }
        return mergedEntity;
	}

	@Override
	public void delete(T entity) throws DaoException {
		EntityManager entityManager = null;
		EntityTransaction tx = null;
        try {
        	entityManager = getEntityManager();
        	tx = entityManager.getTransaction();
        	tx.begin();
			entityManager.remove(entityManager.merge(entity));
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) {
				tx.rollback();
			}
			throw new DaoException("Error deleting entity of type "+(entity==null?"null":entity.getClass()), e);
		} catch (javax.persistence.PersistenceException e) {
			if (tx!=null) {
				tx.rollback();
			}
			throw new DaoException("Error deleting entity of type "+(entity==null?"null":entity.getClass()), e);			
		} finally {
        } 
        entity = null;
	}

	@Override
	public void deleteAll(Collection<T> entities) throws DaoException {
		EntityManager entityManager = null;
		EntityTransaction tx = null;
        try {
        	entityManager = getEntityManager();
        	tx = entityManager.getTransaction();
        	tx.begin();
			for (T entity : entities) {
				entityManager.remove(entityManager.merge(entity));
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) {
				tx.rollback();
			}
			throw new DaoException("Error deleting entities", e);
		} catch (javax.persistence.PersistenceException e) {
			if (tx!=null) {
				tx.rollback();
			}
			throw new DaoException("Error deleting entities", e);			
		} finally {
        } 
        entities = null;
	}
	
	@Override
	public T findById(Serializable id) throws DaoException {
		T entity = null;
		EntityManager entityManager = null;

        try {
        	entityManager = getEntityManager();
			entity = (T) entityManager.find(_entityType, id);
		} catch (HibernateException e) {
			throw new DaoException("Error finding entity of type "+(entity==null?"null":entity.getClass())+" by id="+id, e);
		} finally {
        } 
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(Integer start, Integer maximum) throws DaoException {
		List<T> entities = null;
		EntityManager entityManager = null;

        try {
        	entityManager = getEntityManager();
            Query query = entityManager.createQuery("from "+_entityType.getSimpleName());
            if (maximum!=null) {
            	query.setMaxResults(maximum);
            }
            if (start!=null) {
            	query.setFirstResult(start);
            }
            entities = (List<T>) query.getResultList();
        } catch (Exception e) {
            throw new DaoException("Error retreiving list of entities of type "+_entityType.getCanonicalName(), e);
        } finally {
        }
		
		return entities;
	}
	 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() throws DaoException {
		List<T> entities = null;
		EntityManager entityManager = null;

        try {
        	entityManager = getEntityManager();
            Query query = entityManager.createQuery("from "+_entityType.getSimpleName());
            entities = (List<T>) query.getResultList();
        } catch (Exception e) {
            throw new DaoException("Cannot get all entities of type "+_entityType.getCanonicalName(), e);
        } finally {
        }
		
		return entities;
	}

	@Override
	public void detach(Entity entity) {
		EntityManager manager = null;

		manager = getEntityManager();
		manager.detach(entity);
	}

	@Override
	public boolean isProxy(Object proxy) {
		return proxy instanceof HibernateProxy;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends Entity> Class<E> getClassWithoutInitializingProxy(E entity) {
		return (Class<E>) deproxify(entity).getClass();
	}

	@SuppressWarnings("unchecked")
	public <X> X deproxify(X proxy) {
		X noproxy = null;
		if ( proxy instanceof HibernateProxy) {
			noproxy = (X) ((HibernateProxy) proxy).getHibernateLazyInitializer().getImplementation();
		} else {
			noproxy = proxy;
		}
		return noproxy;
	}
	 
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	 
}

