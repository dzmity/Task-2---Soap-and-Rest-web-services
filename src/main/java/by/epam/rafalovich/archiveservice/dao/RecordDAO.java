package by.epam.rafalovich.archiveservice.dao;

import by.epam.rafalovich.archiveservice.entity.CommunicationRecord;
import by.epam.rafalovich.archiveservice.entity.RecordCriteria;

import java.util.Collection;

/**
 * Created by Dzmitry_Rafalovich on 1/21/2017.
 */
public interface RecordDAO extends GenericDAO<CommunicationRecord> {

    Collection<CommunicationRecord> findRecords(RecordCriteria recordCriteria);
}
