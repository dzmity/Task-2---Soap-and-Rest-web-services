package by.epam.rafalovich.archiveservice.provider.soap.impl;

import by.epam.rafalovich.archiveservice.*;
import by.epam.rafalovich.archiveservice.dao.RecipientDAO;
import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.archiveservice.entity.CommunicationRecord;
import by.epam.rafalovich.archiveservice.entity.Recipient;
import by.epam.rafalovich.archiveservice.entity.RecordCriteria;
import by.epam.rafalovich.archiveservice.entity.Sender;
import by.epam.rafalovich.wsdl.archiveservice_wsdl.ArchivePortType;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dzmitry_Rafalovich on 2/10/2017.
 */
@Service
public class NewArchiveService implements ArchivePortType {

    @Autowired
    RecordDAO recordDAOImpl;

    @Autowired
    RecipientDAO recipientDAOImpl;

    @Autowired
    SenderDAO senderDAOImpl;

    @Autowired
    private Mapper mapper;

    @Override
    public void deleteSender(SenderType senderType) {

        senderDAOImpl.delete(mapper.map(senderType, Sender.class));
    }

    @Override
    public void updateSender(SenderType senderType) {

        senderDAOImpl.update(mapper.map(senderType, Sender.class));
    }

    @Override
    public SenderArchive findSenders() {

        SenderArchive archive = new SenderArchive();
        List<SenderType> senderTypes = archive.getSender();

        List<Sender> senders = senderDAOImpl.findAll();

        for (Sender x : senders) {
            senderTypes.add(mapper.map(x, SenderType.class));
        }

        return archive;
    }

    @Override
    public SenderType findSender(SenderCriteriaType criteriaType) {

        SenderType sender = null;

        BigInteger id = criteriaType.getId();
        String name = criteriaType.getName();
        String email = criteriaType.getEmail();
        String fax = criteriaType.getFax();
        String number = criteriaType.getNumber();

        if (id != null) {
            sender = mapper.map(senderDAOImpl.findById(id.longValue()), SenderType.class);
        } else if (name != null) {
            sender = mapper.map(senderDAOImpl.findSenderBySenderName(name), SenderType.class);
        } else if (email != null) {
            sender = mapper.map(senderDAOImpl.findSenderByEmail(email), SenderType.class);
        } else if (number != null) {
            sender = mapper.map(senderDAOImpl.findSenderByPhoneNumber(number), SenderType.class);
        } else if (fax != null) {
            sender = mapper.map(senderDAOImpl.findSenderByFax(fax), SenderType.class);
        }

        return sender;
    }

    @Override
    public void createSender(SenderType senderType) {

        senderDAOImpl.create(mapper.map(senderType, Sender.class));
    }

    @Override
    public Archive searchRecords(RecordCriteriaType criteriaType) {


        Archive archive = new Archive();
        List<CommunicationRecordType> recordList = archive.getRecord();

        RecordCriteria criteria = mapper.map(criteriaType, RecordCriteria.class);

        String contact = criteriaType.getRecipientContact();

        if (contact != null) {
            Recipient recipient = recipientDAOImpl.findRecipientByContact(contact);
            Long recipientId = recipient != null ? recipient.getRecipientId() : null;
            criteria.setRecipientId(recipientId);
        }

        Collection<CommunicationRecord> records = recordDAOImpl.findRecords(criteria);

        for (CommunicationRecord x : records) {

            CommunicationRecordType record = mapper.map(x, CommunicationRecordType.class);
            recordList.add(record);
        }

        return archive;
    }
}
