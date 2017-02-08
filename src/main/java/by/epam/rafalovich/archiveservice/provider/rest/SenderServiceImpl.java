package by.epam.rafalovich.archiveservice.provider.rest;

import by.epam.rafalovich.archiveservice.Sender;
import by.epam.rafalovich.archiveservice.SenderArchive;
import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.archiveservice.exception.DAOException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by Dzmitry_Rafalovich on 2/6/2017.
 */
@Service("senderService")
public class SenderServiceImpl implements ISenderService {


    @Autowired
    private SenderDAO senderDAOImpl;

    @Autowired
    private Mapper mapper;

    @Override
    public SenderArchive findAllSenders() {

        SenderArchive archive = new SenderArchive();
        List<Sender> senders = archive.getSender();

        try{

            Collection<by.epam.rafalovich.archiveservice.entity.Sender> collection = senderDAOImpl.findAll();

            for (by.epam.rafalovich.archiveservice.entity.Sender x : collection) {
                Sender sender = mapper.map(x, Sender.class);
                senders.add(sender);
            }

        }catch (DAOException e) {
            e.printStackTrace();
        }
        return archive;
    }

    @Override
    public void updateSender(Sender sender) {

        try{

            by.epam.rafalovich.archiveservice.entity.Sender senderDomain = mapper.map(sender,
                    by.epam.rafalovich.archiveservice.entity.Sender.class);

            senderDAOImpl.update(senderDomain);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createSender(Sender sender) {

        try{

            by.epam.rafalovich.archiveservice.entity.Sender senderDomain = mapper.map(sender,
                    by.epam.rafalovich.archiveservice.entity.Sender.class);

            senderDAOImpl.create(senderDomain);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSender(Sender sender) {

        try{

            by.epam.rafalovich.archiveservice.entity.Sender senderDomain = mapper.map(sender,
                    by.epam.rafalovich.archiveservice.entity.Sender.class);

            senderDAOImpl.delete(senderDomain);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Sender findSender(int senderId) {

        Sender sender = null;

        try {
            sender = mapper.map(senderDAOImpl.findById((long) senderId), Sender.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sender;
    }
}
