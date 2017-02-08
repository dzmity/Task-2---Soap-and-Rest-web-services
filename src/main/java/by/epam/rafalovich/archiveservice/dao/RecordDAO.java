package by.epam.rafalovich.archiveservice.dao;


import by.epam.rafalovich.archiveservice.entity.CommunicationRecord;
import by.epam.rafalovich.archiveservice.entity.OperationType;
import by.epam.rafalovich.archiveservice.exception.DAOException;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by Dzmitry_Rafalovich on 1/21/2017.
 */
public interface RecordDAO extends GenericDAO<CommunicationRecord> {

    Collection<CommunicationRecord> findRecordsBySender(Long senderId) throws DAOException;

    Collection<CommunicationRecord> findRecordsByRecipient(Long recipientId) throws DAOException;

    Collection<CommunicationRecord> findRecords(Long senderId, Long recipientId, LocalDateTime startDateTime,
                                                           LocalDateTime endDateTime, OperationType type) throws DAOException;

    /*Collection<CommunicationRecord> findRecordsByOperatioName(String  operation) throws DAOException;

    Collection<CommunicationRecord> findRecordsByDate(LocalDate date) throws DAOException;

    Collection<CommunicationRecord> findRecordsByDate(LocalDate startDate, LocalDate endDate) throws DAOException;*/

}
