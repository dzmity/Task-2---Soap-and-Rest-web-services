package by.epam.rafalovich.archiveservice.dao.impl;

import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.entity.CommunicationRecord;
import by.epam.rafalovich.archiveservice.entity.Operation;

import by.epam.rafalovich.archiveservice.entity.RecordCriteria;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    private static final String SENDER_ID_PROPERTY_NAME = "sender.senderId";
    private static final String RECIPIENT_ID_PROPERTY_NAME = "recipient.recipientId";
    private static final String OPERATION_TYPE_PROPERTY_NAME = "operationType";
    private static final String DATE_TIME_PROPERTY_NAME = "dateTime";
    private static final String QUERY_NAME = "findRecordCount";

    @Override
    public Collection<CommunicationRecord> findRecords(RecordCriteria recordCriteria) {

        Long senderId = recordCriteria.getSenderId();
        Long recipientId = recordCriteria.getRecipientId();
        LocalDateTime startDateTime = recordCriteria.getStartDateTime();
        LocalDateTime endDateTime = recordCriteria.getEndDateTime();
        Operation type = recordCriteria.getOperationType();

        LocalDateTime start = startDateTime != null ? startDateTime : LocalDateTime.of(
                START_YEAR, START_MONTH, START_DAY, START_HOUR, START_MINUTE);
        LocalDateTime end = endDateTime != null ? endDateTime : LocalDateTime.now();

        Criteria criteria = currentSession().createCriteria(CommunicationRecord.class);
        criteria = senderId != null ? criteria.add(Restrictions.eq(SENDER_ID_PROPERTY_NAME, senderId)) : criteria;
        criteria = recipientId != null ? criteria.add(Restrictions.eq(RECIPIENT_ID_PROPERTY_NAME, recipientId)) : criteria;
        criteria = type != null ? criteria.add(Restrictions.eq(OPERATION_TYPE_PROPERTY_NAME, type)) : criteria;
        criteria.add(Restrictions.between(DATE_TIME_PROPERTY_NAME, start,end));

        return criteria.list();
    }

    @Override
    public long findRecordCount() {

        Query query = currentSession().getNamedQuery(QUERY_NAME);
        return (Long) query.uniqueResult();
    }
}
