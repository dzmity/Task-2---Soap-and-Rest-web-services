package by.epam.rafalovich.archiveservice.provider.soap.impl;

import by.epam.rafalovich.archiveservice.*;

import by.epam.rafalovich.archiveservice.dao.SenderDAO;

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
public class SenderServiceImpl  {

    @Autowired
    private Mapper mapper;

    @Autowired
    SenderDAO senderDAOImpl;


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


    public SenderArchive findSenders() {

        SenderArchive archive = new SenderArchive();
        List<SenderType> senders = archive.getSender();

        Collection<by.epam.rafalovich.archiveservice.entity.Sender> collection = senderDAOImpl.findAll();

        for (by.epam.rafalovich.archiveservice.entity.Sender x : collection) {
            SenderType sender = mapper.map(x, SenderType.class);
            senders.add(sender);
        }

        return archive;
    }


    public void updateSender(SenderType sender) {

        by.epam.rafalovich.archiveservice.entity.Sender senderDomain = mapper.map(sender,
                by.epam.rafalovich.archiveservice.entity.Sender.class);

        senderDAOImpl.update(senderDomain);
    }


    public void createSender(SenderType sender) {

        by.epam.rafalovich.archiveservice.entity.Sender senderDomain = mapper.map(sender,
                by.epam.rafalovich.archiveservice.entity.Sender.class);

        senderDAOImpl.create(senderDomain);
    }


    public void deleteSender(SenderType sender) {

        by.epam.rafalovich.archiveservice.entity.Sender senderDomain = mapper.map(sender,
                by.epam.rafalovich.archiveservice.entity.Sender.class);

        senderDAOImpl.delete(senderDomain);
    }
}
