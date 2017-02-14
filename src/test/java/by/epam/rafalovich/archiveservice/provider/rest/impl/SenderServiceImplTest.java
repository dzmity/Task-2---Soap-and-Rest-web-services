package by.epam.rafalovich.archiveservice.provider.rest.impl;

import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.archiveservice.entity.Sender;
import by.epam.rafalovich.archiveservice.entity.SenderInfo;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Dzmitry_Rafalovich on 2/13/2017.
 */
public class SenderServiceImplTest {

    @Mock
    private SenderDAO senderDAOImpl;

    @InjectMocks
    private SenderServiceImpl senderService;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
    }

    private Sender initDomainSender(long id) {

        String senderName = "Test sender ";
        Sender sender = new Sender();
        sender.setSenderId(id);
        sender.setSenderName(senderName + id);

        return sender;
    }

    private SenderInfo initSenderIfo() {

        String email = "testEmail";
        String fax = "testFax";
        String number = "testNumber";

        SenderInfo senderInfo = new SenderInfo();
        senderInfo.setSenderEmail(email);
        senderInfo.setSenderFax(fax);
        senderInfo.setSenderPhoneNumber(number);

        return senderInfo;
    }

    private List<Sender> createTestDomainCollection() {

        int collectionsSize = 5;

        List<Sender> senders = new ArrayList<>();

        for (long i = 0; i < collectionsSize; i++) {
            senders.add(initDomainSender(i));
        }

        return senders;
    }

    @Test
    public void findAllSenders() throws Exception {

        int times = 1;

        List<Sender> domainSenders = createTestDomainCollection();
        when ( senderDAOImpl.findAll() ).thenReturn( domainSenders );
        List<Sender> results = senderService.findAllSenders();

        assertTrue(CollectionUtils.isEqualCollection(results, domainSenders));
        verify(senderDAOImpl, times(times)).findAll();
        verifyNoMoreInteractions( senderDAOImpl );
    }

    @Test
    public void updateSender() throws Exception {

        long id = 2;
        int times = 1;

        Sender sender = initDomainSender(id);
        SenderInfo info = initSenderIfo();
        sender.setInfo(info);

        senderService.updateSender(sender);

        info.setSender(sender);

        verify(senderDAOImpl, times(times)).update(sender);
        verifyNoMoreInteractions(senderDAOImpl);
    }

    @Test
    public void createSender() throws Exception {

        long id = 2;
        int times = 1;

        Sender sender = initDomainSender(id);
        SenderInfo info = initSenderIfo();
        sender.setInfo(info);

        senderService.createSender(sender);

        info.setSender(sender);

        verify(senderDAOImpl, times(times)).create(sender);
        verifyNoMoreInteractions(senderDAOImpl);
    }

    @Test
    public void deleteSender() throws Exception {

        long id = 2;
        int times = 1;
        Sender sender = initDomainSender(id);

        when ( senderDAOImpl.findById(id) ).thenReturn( sender );

        senderService.deleteSender(id);

        verify(senderDAOImpl, times(times)).delete(sender);
        verify(senderDAOImpl, times(times)).findById(id);
        verifyNoMoreInteractions(senderDAOImpl);
    }

    @Test
    public void findSender() throws Exception {

        long id = 2;
        int times = 1;

        Sender sender = initDomainSender(id);

        when ( senderDAOImpl.findById(id) ).thenReturn( sender );

        Sender result = senderService.findSender(id);

        assertEquals(sender, result);

        verify(senderDAOImpl, times(times)).findById(id);
        verifyNoMoreInteractions(senderDAOImpl);
    }
}