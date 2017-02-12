package by.epam.rafalovich.archiveservice.provider.rest.impl;

import by.epam.rafalovich.archiveservice.dao.RecipientDAO;
import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.entity.*;
import by.epam.rafalovich.archiveservice.entity.Operation;
import by.epam.rafalovich.archiveservice.entity.Recipient;
import by.epam.rafalovich.archiveservice.provider.rest.IArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Dzmitry_Rafalovich on 2/6/2017.
 */
@Service("archiveService")
public class ArchiveServiceImpl implements IArchiveService {

    @Autowired
    RecordDAO recordDAOImpl;

    @Autowired
    RecipientDAO recipientDAOImpl;

    @Override
    public List<CommunicationRecord> findRecords(Long senderId, String recipientContact, LocalDateTime startDateTime,
                                                 LocalDateTime endDateTime, Operation operation) {

        Long recipientId = null;

        if (recipientContact != null) {
            Recipient recipient = recipientDAOImpl.findRecipientByContact(recipientContact);
            recipientId = recipient != null ? recipient.getRecipientId() : null;
        }

        RecordCriteria recordCriteria = new RecordCriteria();
        recordCriteria.setSenderId(senderId);
        recordCriteria.setOperationType(operation);
        recordCriteria.setRecipientId(recipientId);
        recordCriteria.setStartDateTime(startDateTime);
        recordCriteria.setEndDateTime(endDateTime);

        return (List) recordDAOImpl.findRecords(recordCriteria);
       //return (List) recordDAOImpl.findRecords(senderId, recipientId, startDateTime, endDateTime, operation);
    }
}
