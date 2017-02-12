package by.epam.rafalovich.archiveservice.provider.rest.impl;

import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.archiveservice.entity.Sender;
import by.epam.rafalovich.archiveservice.provider.rest.ISenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Dzmitry_Rafalovich on 2/6/2017.
 */
@Service("senderServiceREST")
public class SenderServiceImpl implements ISenderService {

    @Autowired
    private SenderDAO senderDAOImpl;

    @Override
    public List<Sender> findAllSenders() {

        return senderDAOImpl.findAll();
    }

    @Override
    public void updateSender(Sender sender) {

        senderDAOImpl.update(sender);
    }

    @Override
    public void createSender(Sender sender) {

        senderDAOImpl.create(sender);
    }

    @Override
    public void deleteSender(long senderId) {

        Sender sender = findSender(senderId);
        senderDAOImpl.delete(sender);
    }

    @Override
    public Sender findSender(long senderId) {

        return senderDAOImpl.findById(senderId);
    }
}
