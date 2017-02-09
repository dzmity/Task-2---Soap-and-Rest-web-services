package by.epam.rafalovich.archiveservice.provider.soap.impl;

import by.epam.rafalovich.archiveservice.*;
import by.epam.rafalovich.archiveservice.Sender;
import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.wsdl.archiveservice_wsdl.SenderPortType;
import org.dozer.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dzmitry_Rafalovich on 1/30/2017.
 */
@Service("senderServiceSOAP")
public class SenderServiceImpl implements SenderPortType {

    @Autowired
    private Mapper mapper;

    @Autowired
    SenderDAO senderDAOImpl;

    @Override
    public Sender findSender(@WebParam(partName = "senderRequest", name = "senderRequest", targetNamespace = "")
                                         SenderCriteriaList senderRequest) {

        Sender sender = null;

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

        return sender;
    }

    @Override
    public SenderArchive findSenders() {

        SenderArchive archive = new SenderArchive();
        List<Sender> senders = archive.getSender();

        Collection<by.epam.rafalovich.archiveservice.entity.Sender> collection = senderDAOImpl.findAll();

        for (by.epam.rafalovich.archiveservice.entity.Sender x : collection) {
            Sender sender = mapper.map(x, Sender.class);
            senders.add(sender);
        }

        return archive;
    }

    @Override
    public void updateSender(@WebParam(partName = "updateRequest", name = "updateRequest", targetNamespace = "") Sender sender) {

        by.epam.rafalovich.archiveservice.entity.Sender senderDomain = mapper.map(sender,
                by.epam.rafalovich.archiveservice.entity.Sender.class);

        senderDAOImpl.update(senderDomain);
    }

    @Override
    public void createSender(@WebParam(partName = "createRequest", name = "createRequest", targetNamespace = "") Sender sender) {

        by.epam.rafalovich.archiveservice.entity.Sender senderDomain = mapper.map(sender,
                by.epam.rafalovich.archiveservice.entity.Sender.class);

        senderDAOImpl.create(senderDomain);
    }

    @Override
    public void deleteSender(@WebParam(partName = "deleteRequest", name = "deleteRequest", targetNamespace = "") Sender sender) {

        by.epam.rafalovich.archiveservice.entity.Sender senderDomain = mapper.map(sender,
                by.epam.rafalovich.archiveservice.entity.Sender.class);

        senderDAOImpl.delete(senderDomain);
    }
}
