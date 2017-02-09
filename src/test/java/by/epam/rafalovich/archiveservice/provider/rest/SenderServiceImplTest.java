package by.epam.rafalovich.archiveservice.provider.rest;

import by.epam.rafalovich.archiveservice.Sender;
import by.epam.rafalovich.archiveservice.SenderArchive;
import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.archiveservice.provider.rest.impl.SenderServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Dzmitry_Rafalovich on 2/8/2017.
 */
public class SenderServiceImplTest {

    @Mock
    private SenderDAO senderDAOImpl;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private SenderServiceImpl senderService;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

    }

    private List<by.epam.rafalovich.archiveservice.entity.Sender> createTestDomainCollection() {

        int collectionsSize = 5;

        List<by.epam.rafalovich.archiveservice.entity.Sender> senders = new ArrayList<>();

        for (long i = 0; i < collectionsSize; i++) {
            senders.add(initDomainSender(i));
        }

        return senders;
    }

    private by.epam.rafalovich.archiveservice.entity.Sender initDomainSender(long id) {

        String senderName = "Test sender ";
        by.epam.rafalovich.archiveservice.entity.Sender sender = new by.epam.rafalovich.archiveservice.entity.Sender();
        sender.setSenderId(id);
        sender.setSenderName(senderName + id);

        return sender;
    }

    private Sender initTestSender(long id) {

        String senderName = "Test sender ";
        Sender sender = new Sender();
        sender.setId(BigInteger.valueOf(id));
        sender.setSenderName(senderName + id);

        return sender;
    }

    private List<Sender> createTestCollection() {

        int collectionsSize = 5;

        List<Sender> senders = new ArrayList<>();

        for (int i = 0; i < collectionsSize; i++) {

            senders.add(initTestSender(i));
        }
        return senders;
    }


    @Test
    public void findAllSenders() throws Exception {

        List<Sender> dtoSenders = createTestCollection();
        List<by.epam.rafalovich.archiveservice.entity.Sender> domainSenders = createTestDomainCollection();

        assertTrue(dtoSenders.size() == domainSenders.size());

        int collectionsSize = domainSenders.size();

        for (int i = 0; i < collectionsSize; i++) {
            when(mapper.map(domainSenders.get(i), Sender.class)).thenReturn(dtoSenders.get(i));
        }
        when ( senderDAOImpl.findAll() ).thenReturn( domainSenders );

        SenderArchive result = senderService.findAllSenders();

        assertTrue(CollectionUtils.isEqualCollection(result.getSender(), dtoSenders));
        verify(senderDAOImpl, times(1)).findAll();
        verifyNoMoreInteractions( senderDAOImpl );
    }

    @Test
    public void updateSender() throws Exception {

        long id = 5;

        by.epam.rafalovich.archiveservice.entity.Sender domainSender = initDomainSender(id);
        Sender sender = initTestSender(id);

        when(mapper.map(sender, by.epam.rafalovich.archiveservice.entity.Sender.class)).thenReturn(domainSender);

        senderService.updateSender(sender);

        verify(senderDAOImpl, times(1)).update(domainSender);
        verify(mapper, times(1)).map(sender, by.epam.rafalovich.archiveservice.entity.Sender.class);
        verifyNoMoreInteractions(senderDAOImpl);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    public void createSender() throws Exception {

        long id = 5;

        by.epam.rafalovich.archiveservice.entity.Sender domainSender = initDomainSender(id);
        Sender sender = initTestSender(id);

        when(mapper.map(sender, by.epam.rafalovich.archiveservice.entity.Sender.class)).thenReturn(domainSender);

        senderService.createSender(sender);

        verify(senderDAOImpl, times(1)).create(domainSender);
        verify(mapper, times(1)).map(sender, by.epam.rafalovich.archiveservice.entity.Sender.class);
        verifyNoMoreInteractions(senderDAOImpl);
        verifyNoMoreInteractions(mapper);

    }

    @Test
    public void deleteSender() throws Exception {

        long id = 5;

        by.epam.rafalovich.archiveservice.entity.Sender domainSender = initDomainSender(id);
        Sender sender = initTestSender(id);

        when(mapper.map(sender, by.epam.rafalovich.archiveservice.entity.Sender.class)).thenReturn(domainSender);

        senderService.deleteSender(sender);

        verify(senderDAOImpl, times(1)).delete(domainSender);
        verify(mapper, times(1)).map(sender, by.epam.rafalovich.archiveservice.entity.Sender.class);
        verifyNoMoreInteractions(senderDAOImpl);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    public void findSender() throws Exception {

        long id = 5;

        by.epam.rafalovich.archiveservice.entity.Sender domainSender = initDomainSender(id);
        Sender sender = initTestSender(id);

        when(mapper.map(domainSender, Sender.class)).thenReturn(sender);
        when(senderDAOImpl.findById(id)).thenReturn(domainSender);

        Sender result = senderService.findSender((int) id);

        assertTrue(result.getId().longValue() == id);
        verify(senderDAOImpl, times(1)).findById(id);
        verify(mapper, times(1)).map(domainSender, Sender.class);
        verifyNoMoreInteractions(senderDAOImpl);
        verifyNoMoreInteractions(mapper);
    }

}