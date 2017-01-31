package by.epam.rafalovich.archiveservice.dao.impl;


import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.entity.CommunicationRecord;
import by.epam.rafalovich.archiveservice.exception.DAOException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Dzmitry_Rafalovich on 1/23/2017.
 */
@Repository
@Transactional
public class RecordDAOImpl extends GenericDAOImpl<CommunicationRecord> implements RecordDAO {

    @Override
    public Collection<CommunicationRecord> findRecordsBySender(Long senderId) throws DAOException {
        Query query = currentSession().getNamedQuery("findBySender").setLong("senderId", senderId);
        return  query.list();
    }

    @Override
    public Collection<CommunicationRecord> findRecordsByRecipient(Long recipientId) throws DAOException {

        Query query = currentSession().getNamedQuery("findByRecipient").setLong("recipientId", recipientId);
        return  query.list();
    }
}
