package by.epam.rafalovich.archiveservice.provider.rest.impl;

import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.archiveservice.entity.Sender;
import by.epam.rafalovich.archiveservice.entity.SenderInfo;
import by.epam.rafalovich.archiveservice.provider.rest.ISenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Dzmitry_Rafalovich on 2/6/2017.
 */
@Service("senderService")
public class SenderServiceImpl implements ISenderService {

    private static final Logger LOG = LoggerFactory.getLogger(SenderServiceImpl.class);

    @Autowired
    private SenderDAO senderDAOImpl;

    @Override
    public List<Sender> findAllSenders() {

        LOG.info("Invocation of findAllSenders method in senderService.");

        return senderDAOImpl.findAll();
    }

    @Override
    public void updateSender(Sender sender) {

        LOG.info("Invocation of updateSender method in senderService with parameter ", sender);

        SenderInfo info = sender.getInfo();
        info.setSender(sender);
        senderDAOImpl.update(sender);
    }

    @Override
    public void createSender(Sender sender) {

        LOG.info("Invocation of createSender method in senderService with parameter ", sender);

        SenderInfo info = sender.getInfo();
        info.setSender(sender);
        senderDAOImpl.create(sender);
    }

    @Override
    public void deleteSender(long senderId) {

        LOG.info("Invocation of deleteSender method in senderService with senderId ", senderId);

        Sender sender = findSender(senderId);
        senderDAOImpl.delete(sender);
    }

    @Override
    public Sender findSender(long senderId) {

        LOG.info("Invocation of findSender method in senderService with senderId ", senderId);

        return senderDAOImpl.findById(senderId);
    }
}
