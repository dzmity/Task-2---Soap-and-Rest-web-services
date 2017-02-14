package by.epam.rafalovich.archiveservice.dao.impl;

import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.archiveservice.entity.Sender;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dzmitry_Rafalovich on 1/23/2017.
 */
@Repository
@Transactional
public class SenderDAOImpl extends GenericDAOImpl<Sender> implements SenderDAO {

    @Override
    public Sender findSenderBySenderName(String name) {

        String queryName = "findByName";
        String propertyName = "name";

        Query query = currentSession().getNamedQuery(queryName).setString(propertyName, name);
        return (Sender) query.uniqueResult();
    }

    @Override
    public Sender findSenderByPhoneNumber(String number) {

        String queryName = "findByNumber";
        String propertyName = "number";

        Query query = currentSession().getNamedQuery(queryName).setString(propertyName, number);
        return (Sender) query.uniqueResult();
    }

    @Override
    public Sender findSenderByFax(String fax) {

        String queryName = "findByFax";
        String propertyName = "fax";

        Query query = currentSession().getNamedQuery(queryName).setString(propertyName, fax);
        return (Sender) query.uniqueResult();
    }

    @Override
    public Sender findSenderByEmail(String email) {

        String queryName = "findByEmail";
        String propertyName = "email";

        Query query = currentSession().getNamedQuery(queryName).setString(propertyName, email);
        return (Sender) query.uniqueResult();
    }
}
