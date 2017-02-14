package by.epam.rafalovich.archiveservice.dao.impl;

import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.archiveservice.entity.Sender;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SenderDAOImpl extends GenericDAOImpl<Sender> implements SenderDAO {

    private static final String FIND_BY_NAME = "findByName";
    private static final String NAME = "name";
    private static final String FIND_BY_NUMBER = "findByNumber";
    private static final String NUMBER = "number";
    private static final String FIND_BY_FAX = "findByFax";
    private static final String FAX = "fax";
    private static final String FIND_BY_EMAIL = "findByEmail";
    private static final String EMAIL = "email";

    @Override
    public Sender findSenderBySenderName(String name) {

        Query query = currentSession().getNamedQuery(FIND_BY_NAME).setString(NAME, name);
        return (Sender) query.uniqueResult();
    }

    @Override
    public Sender findSenderByPhoneNumber(String number) {

        Query query = currentSession().getNamedQuery(FIND_BY_NUMBER).setString(NUMBER, number);
        return (Sender) query.uniqueResult();
    }

    @Override
    public Sender findSenderByFax(String fax) {

        Query query = currentSession().getNamedQuery(FIND_BY_FAX).setString(FAX, fax);
        return (Sender) query.uniqueResult();
    }

    @Override
    public Sender findSenderByEmail(String email) {

        Query query = currentSession().getNamedQuery(FIND_BY_EMAIL).setString(EMAIL, email);
        return (Sender) query.uniqueResult();
    }
}
