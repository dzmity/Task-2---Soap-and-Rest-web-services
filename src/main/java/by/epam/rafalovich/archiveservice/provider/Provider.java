package by.epam.rafalovich.archiveservice.provider;

import by.epam.rafalovich.archiveservice.*;
import by.epam.rafalovich.archiveservice.Sender;
import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.archiveservice.entity.*;
import by.epam.rafalovich.archiveservice.exception.DAOException;
import by.epam.rafalovich.wsdl.archiveservice_wsdl.ArchivePortType;
import org.dozer.Mapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dzmitry_Rafalovich on 1/30/2017.
 */
@Service
public class Provider implements ArchivePortType {

    @Autowired
    RecordDAO recordDAOImpl;

    @Autowired
    private DozerBeanMapperFactoryBean dozerBean;

    @Autowired
    SenderDAO senderDAOImpl;

    @Override
    public Archive findRecords(@WebParam(partName = "request", name = "request", targetNamespace = "") String request) {

        Archive archive = new Archive();
        List<Record> recordList = archive.getRecord();

        try {

            Collection<CommunicationRecord> records = recordDAOImpl.findRecordsBySender((new Long(request)));
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
    public Sender findSender(@WebParam(partName = "senderRequest", name = "senderRequest", targetNamespace = "") SenderCriteriaList senderRequest) {

        Sender sender = null;

        try {

            Mapper mapper = (Mapper) dozerBean.getObject();
            BigInteger id = senderRequest.getId();
            String name = senderRequest.getName();
            String email = senderRequest.getEmail();
            String fax = senderRequest.getFax();
            String number = senderRequest.getNumber();

            if (id != null) {
                sender = mapper.map(senderDAOImpl.findById(id.longValue()), Sender.class);
            } else if (name != null) {
                sender = mapper.map(senderDAOImpl.findSenderBySenderName(name), Sender.class);
            } else if (email != null) {
                sender = mapper.map(senderDAOImpl.findSenderByEmail(email), Sender.class);
            } else if (number != null) {
                sender = mapper.map(senderDAOImpl.findSenderByPhoneNumber(number), Sender.class);
            } else if (fax != null) {
                sender = mapper.map(senderDAOImpl.findSenderByFax(fax), Sender.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sender;
    }

    @Override
    public SenderArchive findSenders() {

        SenderArchive archive = new SenderArchive();
        List<Sender> senders = archive.getSender();

        try{
            Collection<by.epam.rafalovich.archiveservice.entity.Sender> collection = senderDAOImpl.findAll();
            Mapper mapper = (Mapper) dozerBean.getObject();

            for (by.epam.rafalovich.archiveservice.entity.Sender x : collection) {
                Sender sender = mapper.map(x, Sender.class);
                senders.add(sender);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return archive;
    }
}
