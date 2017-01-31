package by.epam.rafalovich.archiveservice.dao.impl;

import by.epam.rafalovich.archiveservice.dao.RecipientDAO;
import by.epam.rafalovich.archiveservice.entity.Recipient;
import by.epam.rafalovich.archiveservice.exception.DAOException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dzmitry_Rafalovich on 1/23/2017.
 */
@Repository
@Transactional
public class RecipientDAOImpl extends GenericDAOImpl<Recipient> implements RecipientDAO {

    @Override
    public Recipient findRecipientByContact(String contact) throws DAOException {

        Query query = currentSession().getNamedQuery("findByContact").setString("contact",contact);
        return (Recipient) query.uniqueResult();
    }
}
