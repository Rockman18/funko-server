package fr.rockman18.funko.server.engine.persistence.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract implementation of the BaseDao
 * 
 * @param <T>
 *            generic type
 * @param <PK>
 *            id class
 */
public abstract class AbstractBaseJpaDao<T, PK extends Serializable> implements BaseDao<T, PK> {
    private Class<T> type = null;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Constructor
     *
     * @param aType
     */
    public AbstractBaseJpaDao(final Class<T> aType) {
	this.type = aType;
    }

    @Override
    @Transactional(value = "transactionManager")
    public void persist(T entity) {
	entityManager.persist(entity);
    }

    @Override
    @Transactional(value = "transactionManager")
    public void persist(Collection<T> entities) {
	for (T entity : entities) {
	    persist(entity);
	}
    }

    @Override
    @Transactional(value = "transactionManager")
    public T merge(T entity) {
	return entityManager.merge(entity);
    }

    @Override
    @Transactional(value = "transactionManager")
    public <RE> RE mergeRelated(RE entity) {
	return entityManager.merge(entity);
    }

    @Override
    @Transactional(value = "transactionManager")
    public Collection<T> merge(Collection<T> entities) {
	Collection<T> mergedResults = new ArrayList<T>(entities.size());
	for (T entity : entities) {
	    mergedResults.add(merge(entity));
	}
	return mergedResults;
    }

    @Override
    @Transactional(value = "transactionManager")
    public <RE> Collection<RE> mergeRelated(Collection<RE> entities) {
	Collection<RE> mergedResults = new ArrayList<RE>(entities.size());
	for (RE entity : entities) {
	    mergedResults.add(mergeRelated(entity));
	}
	return mergedResults;
    }

    @Override
    @Transactional(value = "transactionManager")
    public T read(PK id) {
	if (type == null) {
	    throw new UnsupportedOperationException("The type must be set to use this method.");
	}
	return read(type, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T read(Class<?> clazz, PK id) {
	return (T) entityManager.find(clazz, id);
    }

    @Override
    public T readExclusive(PK id) {
	if (type == null) {
	    throw new UnsupportedOperationException("The type must be set to use this method.");
	}
	return readExclusive(type, id);
    }

    @Override
    @Transactional(value = "transactionManager")
    public void delete(final PK id) {
	T entity = read(id);
	if (entity != null) {
	    delete(entity);
	}
    }

    @Override
    @Transactional(value = "transactionManager")
    public void delete(final T entity) {
	entityManager.remove(entityManager.merge(entity));
    }

    @Override
    @Transactional(value = "transactionManager")
    public void delete(final Collection<T> entities) {
	for (T entity : entities) {
	    delete(entity);
	}
    }

    @Override
    public List<T> find(String queryString, Object... values) throws IllegalArgumentException {
	return findWithMaxResults(queryString, null, values);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findWithMaxResults(String queryString, Integer maxResults, Object... values)
	    throws IllegalArgumentException {
	Query queryObject = entityManager.createQuery(queryString);

	if (maxResults != null && maxResults > 0) {
	    queryObject.setMaxResults(maxResults);
	}

	if (values != null) {
	    for (int i = 0; i < values.length; i++) {
		queryObject.setParameter(i + 1, values[i]);
	    }
	}
	return queryObject.getResultList();
    }

    @Override
    public List<T> find() {
	return find(type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <Y extends T> List<Y> find(Class<Y> clazz) {
	String entityName = getEntityName(clazz);
	Query query = entityManager.createQuery("SELECT instance FROM " + entityName + " instance");
	return query.getResultList();
    }

    /**
     * Returns a list of persisted resources from their identifier.
     *
     * @param ids
     *            the resources identifiers
     */
    @SuppressWarnings("unchecked")
    public List<T> findByIds(List<String> ids) {
	List<T> entities = new ArrayList<T>();

	String in = getForInQuery(ids);
	if (!StringUtils.isBlank(in)) {
	    String entityName = getEntityName(type);
	    Query query = entityManager
		    .createQuery("SELECT instance FROM " + entityName + " instance where instance.id in " + in);
	    entities = query.getResultList();
	}

	return entities;
    }

    @Override
    @Transactional(value = "transactionManager")
    public int count() {
	String entityName = getEntityName(type);
	Query query = entityManager.createQuery("SELECT count(*) FROM " + entityName + " instance");
	Long resultAsLong = (Long) query.getResultList().get(query.getFirstResult());
	return resultAsLong.intValue();
    }

    @Override
    @Transactional(value = "transactionManager")
    public T refresh(final T transientObject) {
	T managedEntity = null;
	if (entityManager.contains(transientObject)) {
	    managedEntity = transientObject;
	} else {
	    managedEntity = entityManager.merge(transientObject);
	}
	entityManager.refresh(managedEntity);
	return managedEntity;
    }

    @Override
    @Transactional(value = "transactionManager")
    public T refresh(final PK id) {
	if (type == null) {
	    throw new UnsupportedOperationException("The type must be set to use this method.");
	}
	T managedEntity = entityManager.find(type, id);
	entityManager.refresh(managedEntity);
	return managedEntity;
    }

    @Override
    @Transactional(value = "transactionManager")
    public Collection<T> refresh(final Collection<T> entities) {
	Collection<T> refreshedResults = new ArrayList<T>(entities.size());
	for (T entity : entities) {
	    refreshedResults.add(refresh(entity));
	}
	return refreshedResults;
    }

    @Override
    public void clear() {
	entityManager.clear();
    }

    @Override
    @Transactional(value = "transactionManager")
    public void flush() {
	entityManager.flush();
    }

    /**
     * Gets the entity name.
     * 
     * @param aType
     *            the a type
     * @return the entity name
     */
    protected <Y extends T> String getEntityName(Class<Y> aType) {
	Entity entity = aType.getAnnotation(Entity.class);
	if (entity == null) {
	    return aType.getSimpleName();
	}
	String entityName = entity.name();

	if (entityName == null) {
	    return aType.getSimpleName();
	} else if (!(entityName.length() > 0)) {
	    return aType.getSimpleName();
	} else {
	    return entityName;
	}
    }

    @SuppressWarnings("unchecked")
    private T readExclusive(Class<?> clazz, PK id) {
	return (T) entityManager.find(clazz, id, LockModeType.PESSIMISTIC_WRITE);
    }

    /**
     * Transform a list of identifier into a String readable by a hql "in"
     * query. Exemple: input is the list containing 2 and 4. Output is
     * ('2','4').
     *
     * @param ids
     *            the list of identifier
     * @return a String readable by a hql "in" query
     */
    protected <E> String getForInQuery(Collection<E> ids) {
	StringBuffer list = new StringBuffer();

	if (ids != null && !ids.isEmpty()) {
	    list.append("(");
	    Iterator<E> i = ids.iterator();
	    while (i.hasNext()) {
		E id = i.next();

		if (id != null) {
		    if (i.hasNext()) {
			list.append("'" + id + "',");
		    } else {
			list.append("'" + id + "'");
		    }
		}
	    }

	    list.append(")");
	}

	return list.toString();
    }

    /**
     * Return a list of values as a hql "in" query
     *
     * @param values
     *            the list of values
     * @return a list of values as a hql "in" query
     */
    private String buildTests(List<String> tests) {
	StringBuffer list = new StringBuffer();

	if (tests != null && !tests.isEmpty()) {
	    list.append("(");
	    for (int i = 0; i < tests.size(); i++) {
		String test = tests.get(i);
		if (test != null) {
		    if (i < tests.size() - 1) {
			list.append("'" + test + "',");
		    } else {
			list.append("'" + test + "'");
		    }
		}
	    }
	    list.append(")");
	}

	return list.toString();
    }

    /**
     * Getting string from Enums to request
     *
     * @param tests
     * @return
     */
    private String buildHQLFromEnum(List<Enum<?>> tests) {
	StringBuffer list = new StringBuffer();

	if (tests != null && !tests.isEmpty()) {
	    list.append("(");
	    for (int i = 0; i < tests.size(); i++) {
		Enum<?> test = tests.get(i);
		if (test != null) {
		    if (i < tests.size() - 1) {
			list.append("'" + test + "',");
		    } else {
			list.append("'" + test + "'");
		    }
		}
	    }
	    list.append(")");
	}

	return list.toString();
    }

    /**
     * @return the logger
     */
    public Logger getLogger() {
	return logger;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(Class<T> type) {
	this.type = type;
    }

}