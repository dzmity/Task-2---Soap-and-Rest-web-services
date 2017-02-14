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

    @Override
    public Collection<CommunicationRecord> findRecords(RecordCriteria recordCriteria) {

        String senderIdPropertyName = "sender.senderId";
        String recipientIdPropertyName = "recipient.recipientId";
        String operationTypePropertyName = "operationType";
        String dateTimePropertyName = "dateTime";

        Long senderId = recordCriteria.getSenderId();
        Long recipientId = recordCriteria.getRecipientId();
        LocalDateTime startDateTime = recordCriteria.getStartDateTime();
        LocalDateTime endDateTime = recordCriteria.getEndDateTime();
        Operation type = recordCriteria.getOperationType();

        LocalDateTime start = startDateTime != null ? startDateTime : LocalDateTime.of(
                START_YEAR, START_MONTH, START_DAY, START_HOUR, START_MINUTE);
        LocalDateTime end = endDateTime != null ? endDateTime : LocalDateTime.now();

        Criteria criteria = currentSession().createCriteria(CommunicationRecord.class);
        criteria = senderId != null ? criteria.add(Restrictions.eq(senderIdPropertyName, senderId)) : criteria;
        criteria = recipientId != null ? criteria.add(Restrictions.eq(recipientIdPropertyName, recipientId)) : criteria;
        criteria = type != null ? criteria.add(Restrictions.eq(operationTypePropertyName, type)) : criteria;
        criteria.add(Restrictions.between(dateTimePropertyName, start,end));

        return criteria.list();
    }

    @Override
    public long findRecordCount() {

        String queryName = "findRecordCount";

        Query query = currentSession().getNamedQuery(queryName);
        return (Long) query.uniqueResult();
    }
}
