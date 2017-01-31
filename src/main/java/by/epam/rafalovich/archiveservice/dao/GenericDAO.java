package by.epam.rafalovich.archiveservice.dao;

import by.epam.rafalovich.archiveservice.exception.DAOException;

import java.util.List;

public interface GenericDAO<T> {

    /**
     * Add model object to the database operation
     * @param t
     *        Model object that must be added to the database
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs.
     */
    void create(T t) throws DAOException;

    /**
     * Find model object in the database operation
     * @param id
     *        id of model object that must be found in the database
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     *        if model object not found;
     *        if this operation unsupported in specific DAO.
     */
    T findById(Long id) throws DAOException;

    /**
     * Update model object in the database operation
     * @param t
     *        New model object that must be added to the database replaced
     *        the old
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     *        if this operation unsupported in specific DAO.
     */
    void update(T t) throws DAOException;

    /**
     * Delete model object from the database operation
     *
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     *        if this operation unsupported in specific DAO.
     */
    void delete(T t) throws DAOException;

    /**
     * Find all model objects in the database operation
     * @return {@link List} of model objects
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     *        if this operation unsupported in specific DAO.
     */
    List<T> findAll() throws DAOException;
}
