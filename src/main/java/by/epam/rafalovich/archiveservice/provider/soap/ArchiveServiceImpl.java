package by.epam.rafalovich.archiveservice.provider.soap;

import by.epam.rafalovich.archiveservice.Archive;
import by.epam.rafalovich.archiveservice.CriteriaList;
import by.epam.rafalovich.archiveservice.Record;
import by.epam.rafalovich.archiveservice.dao.RecipientDAO;
import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.entity.CommunicationRecord;
import by.epam.rafalovich.archiveservice.entity.OperationType;
import by.epam.rafalovich.archiveservice.entity.Recipient;
import by.epam.rafalovich.wsdl.archiveservice_wsdl.ArchivePortTypeNew;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dzmitry_Rafalovich on 2/8/2017.
 */
@Service
public class ArchiveServiceImpl implements ArchivePortTypeNew {

    @Autowired
    RecordDAO recordDAOImpl;

    @Autowired
    RecipientDAO recipientDAOImpl;

    @Autowired
    private Mapper mapper;

    @Override
    public Archive searchRecords(@WebParam(partName = "request", name = "request", targetNamespace = "") CriteriaList criteriaList) {

        Archive archive = new Archive();
        List<Record> recordList = archive.getRecord();

        Collection<CommunicationRecord> records;

        if (criteriaList == null) {
            records = recordDAOImpl.findAll();
        } else {

            String contact = criteriaList.getRecipientContact();
            Long recipientId = null;

            if (contact != null) {
                Recipient recipient = recipientDAOImpl.findRecipientByContact(contact);
                recipientId = recipient != null ? recipient.getRecipientId() : null;
            }

            Long senderId = criteriaList.getSenderId() != null ? criteriaList.getSenderId().longValue() : null;

            LocalDateTime start = criteriaList.getStartDateTime() != null ?
                    mapper.map(criteriaList.getStartDateTime(), LocalDateTime.class) : null;

            LocalDateTime end = criteriaList.getEndDateTime() != null ?
                    mapper.map(criteriaList.getEndDateTime(), LocalDateTime.class) : null;

            by.epam.rafalovich.archiveservice.entity.OperationType type = criteriaList.getOperationType() != null ?
                    mapper.map(criteriaList.getOperationType(), OperationType.class) : null;

            records = recordDAOImpl.findRecords(recipientId, senderId, start, end, type);
        }

        for (CommunicationRecord x : records) {
            Record record = mapper.map(x, Record.class);
            recordList.add(record);
        }

        return archive;

    }
}
