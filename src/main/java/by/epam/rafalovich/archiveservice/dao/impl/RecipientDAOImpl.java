package by.epam.rafalovich.archiveservice.dao.impl;

import by.epam.rafalovich.archiveservice.dao.RecipientDAO;
import by.epam.rafalovich.archiveservice.entity.Recipient;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dzmitry_Rafalovich on 1/23/2017.
 */
@Repository
@Transactional
public class RecipientDAOImpl extends GenericDAOImpl<Recipient> implements RecipientDAO {

    public static final String FIND_BY_CONTACT = "findByContact";
    public static final String CONTACT = "contact";

    @Override
    public Recipient findRecipientByContact(String contact) {

        Query query = currentSession().getNamedQuery(FIND_BY_CONTACT).setString(CONTACT, contact);
        return (Recipient) query.uniqueResult();
    }
}
