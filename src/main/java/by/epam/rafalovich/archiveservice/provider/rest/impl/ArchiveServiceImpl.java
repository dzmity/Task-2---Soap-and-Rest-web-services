package by.epam.rafalovich.archiveservice.provider.rest.impl;

import by.epam.rafalovich.archiveservice.dao.RecipientDAO;
import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.entity.*;
import by.epam.rafalovich.archiveservice.entity.Operation;
import by.epam.rafalovich.archiveservice.entity.Recipient;
import by.epam.rafalovich.archiveservice.provider.rest.IArchiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Dzmitry_Rafalovich on 2/6/2017.
 */
@Service("recordService")
public class ArchiveServiceImpl implements IArchiveService {

    private static final String DATE_TIME_FORMATTER = "yyyy-MM-dd_HH:mm:ss";
    private static final Logger LOG = LoggerFactory.getLogger(ArchiveServiceImpl.class);

    @Autowired
    RecordDAO recordDAOImpl;

    @Autowired
    RecipientDAO recipientDAOImpl;

    @Override
    public List<CommunicationRecord> findRecords(Long senderId, String recipientContact, String startDateTime,
                                                 String endDateTime, Operation operation) {

        LOG.info("Invocation of findRecords method in archiveService with parameters ", senderId, recipientContact,
                startDateTime, endDateTime, operation);

        Long recipientId = null;

        if (recipientContact != null) {
            Recipient recipient = recipientDAOImpl.findRecipientByContact(recipientContact);
            recipientId = recipient != null ? recipient.getRecipientId() : null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER);
        LocalDateTime start = startDateTime != null ? LocalDateTime.parse(startDateTime, formatter) : null;
        LocalDateTime end = startDateTime != null ? LocalDateTime.parse(endDateTime, formatter) : null;

        RecordCriteria recordCriteria = new RecordCriteria();
        recordCriteria.setSenderId(senderId);
        recordCriteria.setOperationType(operation);
        recordCriteria.setRecipientId(recipientId);
        recordCriteria.setStartDateTime(start);
        recordCriteria.setEndDateTime(end);

        List<CommunicationRecord> result = (List) recordDAOImpl.findRecords(recordCriteria);
        LOG.info("Returns list of communication records with size=", result.size());
        return result;
    }
}
