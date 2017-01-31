package by.epam.rafalovich.archiveservice.dao.impl;

import by.epam.rafalovich.archiveservice.dao.GenericDAO;
import by.epam.rafalovich.archiveservice.exception.DAOException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Dzmitry_Rafalovich on 1/21/2017.
 */
@Repository
@Transactional(readOnly = true)
public abstract class GenericDAOImpl<T> implements GenericDAO<T> {

    @Autowired
    private SessionFactory sessionFactory;

    protected Class<? extends T> daoType;

    public GenericDAOImpl() {

        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        daoType = (Class) pt.getActualTypeArguments()[0];
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional
    public void create(T t) throws DAOException {

        Session session =  currentSession();
        session.save(t);
    }

    @Override
    public T findById(Long id) throws DAOException {

        return currentSession().get(daoType, id);
    }

    @Override
    @Transactional
    public void update(T t) throws DAOException {

        currentSession().merge(t);
    }

    @Override
    @Transactional
    public void delete(T t) throws DAOException {

        currentSession().delete(t);
    }

    @Override
    public List<T> findAll() throws DAOException {

        return currentSession().createCriteria(daoType).list();
    }
}
