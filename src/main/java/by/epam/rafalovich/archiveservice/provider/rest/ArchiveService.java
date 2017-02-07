package by.epam.rafalovich.archiveservice.provider.rest;

import by.epam.rafalovich.archiveservice.*;
import by.epam.rafalovich.archiveservice.dao.RecipientDAO;
import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.archiveservice.entity.*;
import by.epam.rafalovich.archiveservice.entity.OperationType;
import by.epam.rafalovich.archiveservice.entity.Recipient;
import org.dozer.Mapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.QueryParam;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dzmitry_Rafalovich on 2/6/2017.
 */
@Service("archiveService")
public class ArchiveService implements IArchiveService {

    @Autowired
    RecordDAO recordDAOImpl;

    @Autowired
    RecipientDAO recipientDAOImpl;

    @Autowired
    private DozerBeanMapperFactoryBean dozerBean;

    @Override
    public Archive findRecordsBySender(long senderId) {

        Archive archive = new Archive();
        List<Record> recordList = archive.getRecord();

        try {

            Collection<CommunicationRecord> records = recordDAOImpl.findRecordsBySender(((senderId)));
            Mapper mapper = (Mapper) dozerBean.getObject();

            for (CommunicationRecord x : records) {
                Record record = mapper.map(x, Record.class);
                recordList.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return archive;
    }

    @Override
    public Archive findRecordsJSON(CriteriaList criteriaList) {


        Archive archive = new Archive();
        List<Record> recordList = archive.getRecord();

        try {

            Collection<CommunicationRecord> records;

            Mapper mapper = (Mapper) dozerBean.getObject();

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


        } catch (Exception e) {
            e.printStackTrace();
        }

        return archive;
    }

    @Override
    public Archive findRecords( BigInteger id,  String recipientContact,
        XMLGregorianCalendar startDateTime,  XMLGregorianCalendar endDateTime,
         by.epam.rafalovich.archiveservice.OperationType operationType) {

        Archive archive = new Archive();
        List<Record> recordList = archive.getRecord();

        try {

            Collection<CommunicationRecord> records;

            Mapper mapper = (Mapper) dozerBean.getObject();

            Long recipientId = null;

            if (recipientContact != null) {
                Recipient recipient = recipientDAOImpl.findRecipientByContact(recipientContact);
                recipientId = recipient != null ? recipient.getRecipientId() : null;
            }

            Long senderId = id != null ? id.longValue() : null;

            LocalDateTime start = startDateTime != null ? mapper.map(startDateTime, LocalDateTime.class) : null;
            LocalDateTime end = endDateTime != null ? mapper.map(endDateTime, LocalDateTime.class) : null;
            by.epam.rafalovich.archiveservice.entity.OperationType type = operationType != null ?
                    mapper.map(operationType, OperationType.class) : null;

            records = recordDAOImpl.findRecords(recipientId, senderId, start, end, type);

            for (CommunicationRecord x : records) {
                Record record = mapper.map(x, Record.class);
                recordList.add(record);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return archive;
    }
}
