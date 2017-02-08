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

        Query query = currentSession().getNamedQuery("findByName").setString("name", name);
        return (Sender) query.uniqueResult();
    }

    @Override
    public Sender findSenderByPhoneNumber(String number) {

        Query query = currentSession().getNamedQuery("findByNumber").setString("number", number);
        return (Sender) query.uniqueResult();
    }

    @Override
    public Sender findSenderByFax(String fax) {

        Query query = currentSession().getNamedQuery("findByFax").setString("fax", fax);
        return (Sender) query.uniqueResult();
    }

    @Override
    public Sender findSenderByEmail(String email) {

        Query query = currentSession().getNamedQuery("findByEmail").setString("email", email);
        return (Sender) query.uniqueResult();
    }
}
