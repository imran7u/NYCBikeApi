package com.nyc.station.entity.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.proxy.HibernateProxy;

import com.nyc.station.entity.model.Entity;

public class GenericHibernateDao {
	
	@PersistenceUnit(unitName="emf")
	private EntityManagerFactory emf;
	
	public <E extends Entity> void saveAll(Collection<E> entities) throws DaoException {
		EntityTransaction tx = null;
		EntityManager entityManager = null;
		
        try {
        	entityManager = getEntityManager();
	        tx = entityManager.getTransaction();
	        tx.begin();
	        for (E entity : entities) {
	        	entity = entityManager.merge(entity);	
	        }
	        entityManager.flush();
	        tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) {
				tx.rollback();
			}
			throw new DaoException("Error saving entities", e);
		} catch (javax.persistence.PersistenceException e) {
			if (tx!=null) {
				tx.rollback();
			}
			throw new DaoException("Error saving entities", e);
		}  finally {
        }
	}
	
	public <E extends Entity> E update(E entity) throws DaoException {
		EntityManager manager  = null;
		EntityTransaction tx = null;
		try {
			manager = getEntityManager();
	        tx = manager.getTransaction();
	        tx.begin();
	        entity = manager.merge(entity);
	        manager.flush();
	        tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) {
				tx.rollback();
			}
			throw new DaoException("Error saving entity", e);
		} catch (PersistenceException e) {
			if (tx!=null) {
				tx.rollback();
			}
			throw new DaoException("Error saving entity", e);			
		} finally {
		}
		return entity;
	}
	
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public <E extends Entity> List<E> getAll(Class<E> entityClass, Integer start, Integer maximum) throws DaoException {
		List<E> entities = null;
		EntityManager manager = null;
		Query query = null;
        try {
            manager = getEntityManager();
            query = manager.createQuery("from "+entityClass.getSimpleName());
            query.setMaxResults(maximum);
            query.setFirstResult(start);
            entities = (List<E>) query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
        }
		return entities;
	}

	public List execute(String query, Map<String, Object> queryParameters,
			Integer start, Integer maximum) throws DaoException {
		List results = null;
		EntityManager manager = null;

        try {
            manager = getEntityManager();
            Query hqlQuery = manager.createQuery(query);
            if (queryParameters!=null && !queryParameters.isEmpty()) {
            	for (String param : queryParameters.keySet()) {
            		hqlQuery.setParameter(param, queryParameters.get(param));
            	}
            }
            
            hqlQuery.setMaxResults(maximum);
            hqlQuery.setFirstResult(start);
            
            results = hqlQuery.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
        }
		
		return results;
		
	}

	public <E extends Entity> E find(Class<E> entityType, Serializable id) throws DaoException {
		E entity = null;
		EntityManager manager = null;
		try {
			manager = getEntityManager();
			entity = (E) manager.find(entityType, id);
		} catch (HibernateException e) {
			throw new DaoException(e);
		} finally {
		}
		return entity;
	}
	
	public void detach(Entity entity) {
		EntityManager manager = null;
		
		manager = getEntityManager();
		manager.detach(entity);
	}

	public <E extends Entity> void delete(E entity) throws DaoException {
		EntityManager entityManager = null;
		EntityTransaction tx = null;
        try {
        	entityManager = getEntityManager();
        	tx = entityManager.getTransaction();
        	tx.begin();
			entityManager.remove(entityManager.merge(entity));
			tx.commit();
		} catch (HibernateException e) {
			throw new DaoException("Error deleting entity of type "+(entity==null?"null":entity.getClass()), e);
		} catch (PersistenceException e) {
			if (tx!=null) {
				tx.rollback();
			}
			throw new DaoException("Error deleting entity of type "+(entity==null?"null":entity.getClass()), e);
		} finally {
        } 
        entity = null;
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Entity> Class<E> getClassWithoutInitializingProxy(E wrappedObject) {
		if (wrappedObject instanceof HibernateProxy) {
			return (Class<E>) ((HibernateProxy) wrappedObject).getHibernateLazyInitializer().getImplementation().getClass();
		}
		return (Class<E>) wrappedObject.getClass();
	}
	
	public static boolean isProxy(Object proxy) {
		return proxy instanceof HibernateProxy;
	}

	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T unproxy(T proxied)
	{
	    T entity = proxied;
	    if (entity != null && entity instanceof HibernateProxy) {
	        Hibernate.initialize(entity);
	        entity = (T) ((HibernateProxy) entity)
	                  .getHibernateLazyInitializer()
	                  .getImplementation();
	    }
	    return entity;
	}
	
}
