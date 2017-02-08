package by.epam.rafalovich.archiveservice.dao.impl;


import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.entity.CommunicationRecord;
import by.epam.rafalovich.archiveservice.entity.OperationType;
import by.epam.rafalovich.archiveservice.exception.DAOException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by Dzmitry_Rafalovich on 1/23/2017.
 */
@Repository
@Transactional
public class RecordDAOImpl extends GenericDAOImpl<CommunicationRecord> implements RecordDAO {

    private static final int START_YEAR = 2015;
    private static final int START_MONTH = 1;
    private static final int START_DAY = 1;
    private static final int START_HOUR= 0;
    private static final int START_MINUTE= 0;

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

    @Override
    public Collection<CommunicationRecord> findRecords(Long senderId, Long recipientId, LocalDateTime startDateTime,
                                                       LocalDateTime endDateTime, OperationType type) throws DAOException {


        LocalDateTime start = startDateTime != null ? startDateTime : LocalDateTime.of(
                START_YEAR, START_MONTH, START_DAY, START_HOUR, START_MINUTE);
        LocalDateTime end = endDateTime != null ? endDateTime : LocalDateTime.now();

        Criteria criteria = currentSession().createCriteria(CommunicationRecord.class);
        criteria = senderId != null ? criteria.add(Restrictions.eq("sender.senderId", senderId)) : criteria;
        criteria = recipientId != null ? criteria.add(Restrictions.eq("recipient.recipientId", recipientId)) : criteria;
        criteria = type != null ? criteria.add(Restrictions.eq("operationType", type)) : criteria;
        criteria.add(Restrictions.between("dateTime", start,end));

        return criteria.list();
    }
}
