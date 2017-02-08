package by.epam.rafalovich.archiveservice.dao;

import java.util.List;

public interface GenericDAO<T> {

    /**
     * Add model object to the database operation
     * @param t
     *        Model object that must be added to the database
     */
    void create(T t);

    /**
     * Find model object in the database operation
     * @param id
     *        id of model object that must be found in the database
     */
    T findById(Long id);

    /**
     * Update model object in the database operation
     * @param t
     *        New model object that must be added to the database replaced
     *        the old
     */
    void update(T t);

    /**
     * Delete model object from the database operation
     *
     */
    void delete(T t);

    /**
     * Find all model objects in the database operation
     * @return {@link List} of model objects
     */
    List<T> findAll();
}
