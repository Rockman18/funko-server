package fr.rockman18.funko.server.engine.persistence.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * This is our generic DAO interface used for all DAOs
 * 
 * @param <T>
 *            generic type
 * @param <PK>
 *            id class
 */
public interface BaseDao<T, PK extends Serializable> {

    /**
     * Persist the entity. Details of this method are in section 3.2.1 of the
     * <a href="http://tinyurl.com/2pc93u">JPA spec</a>. Basics - persist will
     * take the entity and put it into the db.
     * 
     * @param entity
     */
    public void persist(T entity);

    /**
     * Persist the entities. Details of this method are in section 3.2.1 of the
     * <a href="http://tinyurl.com/2pc93u">JPA spec</a>. Basics - persist will
     * take the entity and put it into the db.
     * 
     * @param entity
     */
    public void persist(Collection<T> entities);

    /**
     * Merge the entity, returning (a potentially different object) the
     * persisted entity. Details of this method are in section 3.2.4.1 of the
     * <a href="http://tinyurl.com/2pc93u">JPA spec</a>. Basics - merge will
     * take an exiting 'detached' entity and merge its properties onto an
     * existing entity. The entity with the merged state is returned.
     * 
     * @param entity
     */
    public T merge(T entity);

    /**
     * Merge a related entity into the persistent context. Management of this
     * related entity may be required to detect changes to bidirectional
     * relationships.
     * 
     * @param relatedEntity
     * @return a managed instance of the relatedEntity argument
     */
    public <RE> RE mergeRelated(RE relatedEntity);

    /**
     * Merge the collection of entities, returning (a collection of potentially
     * different objects) the persisted entities. Details of this method are in
     * section 3.2.4.1 of the <a href="http://tinyurl.com/2pc93u">JPA spec</a>.
     * Basics - merge will take an exiting 'detached' entity and merge its
     * properties onto an existing entity. The entity with the merged state is
     * returned.
     * 
     * @param a
     *            collection of entities
     * @return a collection of managed entities
     */
    public Collection<T> merge(Collection<T> entities);

    /**
     * Merge the collection of related entities, returning the persisted
     * entities (potentially different instances). Details of this method are in
     * section 3.2.4.1 of the <a href="http://tinyurl.com/2pc93u">JPA spec</a>.
     * Basics - merge will take an exiting 'detached' entity and merge its
     * properties onto an existing entity. The entity with the merged state is
     * returned.
     * 
     * @param a
     *            collection of entities
     * @return a collection of managed entities
     */
    public <RE> Collection<RE> mergeRelated(Collection<RE> entities);

    /**
     * Retrieve an object that was previously persisted to the database using
     * the indicated id as primary key
     * 
     * @param id
     *            The Primary Key of the object to get.
     * @return Type
     */
    public T read(PK id);

    /**
     * Retrieve an object that was previously persisted to the database using
     * the indicated id as primary key
     * 
     * @param id
     *            The Primary Key of the object to get.
     * @return Type
     */
    public T read(Class<?> clazz, PK id);

    /**
     * Retrieves an entity that was previously persisted to the database using
     * the parameter as the primary key, and maintain an exclusive lock on that
     * entity's database row(s) until the transaction is committed. Note that
     * this method must be executed with the bounds of a transaction.
     * 
     * @param id
     *            The Primary Key of the entity to retrieve.
     * @return Type
     */
    public T readExclusive(PK id);

    /**
     * Remove an object from persistent storage in the database. Since this uses
     * the PK to do the delete there is a chance that the entity manager could
     * be left in an inconsistent state, if you delete the object with id 1 but
     * that object is still in the entity managers cache. You can do two things,
     * put all your PK deletes together and then call flushAndClear when done,
     * or you can just call the delete method with the entity which will not
     * suffer from this problem.
     * 
     * @see delete(T entity)
     * @param id
     *            The Primary Key of the object to delete.
     */
    public void delete(PK id);

    /**
     * Remove an entity from persistent storage in the database. If the entity
     * is not in the 'managed' state, it is merged into the persistent context
     * then removed.
     * 
     * @param entity
     *            The Primary Key of the object to delete.
     */
    public void delete(T entity);

    /**
     * Remove a collection of entities from persistent storage in the database.
     * 
     * @param entity
     *            The Primary Key of the object to delete.
     */
    public void delete(Collection<T> entities);

    /**
     * Find by entity class.
     *
     * @param clazz
     *            the entity class
     * @return list of entity found
     */
    public <Y extends T> List<Y> find(Class<Y> clazz);

    /**
     * Find by specific queryString which contains parameter to replace by
     * values
     * 
     * @param queryString
     * @param values
     * @return list object's found
     */
    public List<T> find(String queryString, Object... values);

    /**
     * Find maxResults results by specific queryString which contains parameter
     * to replace by values
     *
     * @param queryString
     * @param maxResults
     * @param values
     * @return List<T>
     */
    public List<T> findWithMaxResults(String queryString, Integer maxResults, Object... values);

    /**
     * Find all.
     * 
     * @return the list
     */
    public List<T> find();

    /**
     * Count.
     * 
     * @return the int
     */
    public int count();

    /**
     * Refresh an entity that may have changed in another thread/transaction. If
     * the entity is not in the 'managed' state, it is first merged into the
     * persistent context, then refreshed.
     * 
     * @param transientObject
     *            The Object to refresh.
     */
    public T refresh(T transientObject);

    /**
     * Refresh an entity that may have changed in another thread/transaction. If
     * the entity is not in the 'managed' state, it is located using
     * EntityManager.find() then refreshed.
     * 
     * @param transientObject
     *            The Object to refresh.
     */
    public T refresh(PK id);

    /**
     * Refresh a collection of entities that may have changed in another
     * thread/transaction.
     * 
     * @param transientObject
     *            The Object to refresh.
     */
    public Collection<T> refresh(Collection<T> entities);

    /**
     * Clear the persistence context, causing all managed entities to become
     * detached. Changes made to entities that have not been flushed to the
     * database will not be persisted.
     */
    public void clear();

    /**
     * Synchronize the persistence context to the underlying database.
     */
    public void flush();
}