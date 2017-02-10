package by.epam.rafalovich.archiveservice.dao;

import by.epam.rafalovich.archiveservice.entity.CommunicationRecord;
import by.epam.rafalovich.archiveservice.entity.Operation;
import by.epam.rafalovich.archiveservice.entity.RecordCriteria;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by Dzmitry_Rafalovich on 1/21/2017.
 */
public interface RecordDAO extends GenericDAO<CommunicationRecord> {

    Collection<CommunicationRecord> findRecordsBySender(Long senderId);

    Collection<CommunicationRecord> findRecordsByRecipient(Long recipientId);

    Collection<CommunicationRecord> findRecords(Long senderId, Long recipientId, LocalDateTime startDateTime,
                                                           LocalDateTime endDateTime, Operation type);

    Collection<CommunicationRecord> findRecords(RecordCriteria recordCriteria);
}
