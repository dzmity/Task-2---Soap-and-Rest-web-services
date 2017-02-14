package by.epam.rafalovich.archiveservice.provider.soap.impl;

import by.epam.rafalovich.archiveservice.*;
import by.epam.rafalovich.archiveservice.dao.RecipientDAO;
import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.archiveservice.entity.*;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Dzmitry_Rafalovich on 2/13/2017.
 */

public class NewArchiveServiceTest {

    @Mock
    private RecordDAO recordDAO;

    @Mock
    private SenderDAO senderDAOImpl;

    @Mock
    private RecipientDAO recipientDAO;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private NewArchiveService archiveService;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
    }

    private List<Sender> createTestDomainCollection() {

        int collectionsSize = 5;

        List<Sender> senders = new ArrayList<>();

        for (long i = 0; i < collectionsSize; i++) {
            senders.add(initDomainSender(i));
        }

        return senders;
    }

    private Sender initDomainSender(long id) {

        String senderName = "Test sender ";
        Sender sender = new Sender();
        sender.setSenderId(id);
        sender.setSenderName(senderName + id);

        return sender;
    }

    private SenderType initTestSender(long id) {

        String senderName = "Test sender ";
        SenderType sender = new SenderType();
        sender.setId(BigInteger.valueOf(id));
        sender.setSenderName(senderName + id);

        return sender;
    }

    private List<SenderType> createTestCollection() {

        int collectionsSize = 5;

        List<SenderType> senders = new ArrayList<>();

        for (int i = 0; i < collectionsSize; i++) {

            senders.add(initTestSender(i));
        }
        return senders;
    }

    private SenderCriteriaType initCriteria(BigInteger senderId, String name, String email, String fax, String number) {

        SenderCriteriaType senderCriteriaType = new SenderCriteriaType();
        senderCriteriaType.setId(senderId);
        senderCriteriaType.setName(name);
        senderCriteriaType.setEmail(email);
        senderCriteriaType.setFax(fax);
        senderCriteriaType.setNumber(number);

        return senderCriteriaType;
    }

    @Test
    public void deleteSender() throws Exception {

        long id = 5;
        int times = 1;

        by.epam.rafalovich.archiveservice.entity.Sender domainSender = initDomainSender(id);
        SenderType sender = initTestSender(id);

        when(mapper.map(sender, Sender.class)).thenReturn(domainSender);

        archiveService.deleteSender(sender);

        verify(senderDAOImpl, times(times)).delete(domainSender);
        verify(mapper, times(times)).map(sender, Sender.class);
        verifyNoMoreInteractions(senderDAOImpl);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    public void updateSender() throws Exception {

        long id = 5;
        int times = 1;

        Sender domainSender = initDomainSender(id);
        SenderType sender = initTestSender(id);

        when(mapper.map(sender, Sender.class)).thenReturn(domainSender);

        archiveService.updateSender(sender);

        verify(senderDAOImpl, times(times)).update(domainSender);
        verify(mapper, times(times)).map(sender, Sender.class);
        verifyNoMoreInteractions(senderDAOImpl);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    public void findSenders() throws Exception {

        int times = 1;

        List<SenderType> dtoSenders = createTestCollection();
        List<Sender> domainSenders = createTestDomainCollection();

        assertTrue(dtoSenders.size() == domainSenders.size());

        int collectionsSize = domainSenders.size();

        for (int i = 0; i < collectionsSize; i++) {
            when(mapper.map(domainSenders.get(i), SenderType.class)).thenReturn(dtoSenders.get(i));
        }
        when ( senderDAOImpl.findAll() ).thenReturn( domainSenders );

        SenderArchive result = archiveService.findSenders();

        assertTrue(CollectionUtils.isEqualCollection(result.getSender(), dtoSenders));
        verify(senderDAOImpl, times(times)).findAll();
        verifyNoMoreInteractions( senderDAOImpl );
    }

    @Test
    public void findSenderById() throws Exception {

        long id = 5;
        int times = 1;

        BigInteger senderId = BigInteger.valueOf(id);

        SenderCriteriaType senderCriteriaType = initCriteria(senderId, null, null, null, null);
        Sender domainSender = initDomainSender(id);
        SenderType sender = initTestSender(id);

        when(mapper.map(domainSender, SenderType.class)).thenReturn(sender);
        when(senderDAOImpl.findById(id)).thenReturn(domainSender);

        SenderType result = archiveService.findSender((senderCriteriaType));

        assertTrue(result.getId().longValue() == id);
        verify(senderDAOImpl, times(times)).findById(id);
        verify(mapper, times(times)).map(domainSender, SenderType.class);
        verifyNoMoreInteractions(senderDAOImpl);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    public void findSenderByName() throws Exception {

        long id = 5;
        int times = 1;

        String name = "testName";

        SenderCriteriaType senderCriteriaType = initCriteria(null, name, null, null, null);
        Sender domainSender = initDomainSender(id);
        SenderType sender = initTestSender(id);

        when(mapper.map(domainSender, SenderType.class)).thenReturn(sender);
        when(senderDAOImpl.findSenderBySenderName(name)).thenReturn(domainSender);

        SenderType result = archiveService.findSender((senderCriteriaType));

        assertTrue(result.getId().longValue() == id);
        verify(senderDAOImpl, times(times)).findSenderBySenderName(name);
        verify(mapper, times(times)).map(domainSender, SenderType.class);
        verifyNoMoreInteractions(senderDAOImpl);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    public void findSenderByEmail() throws Exception {

        long id = 5;
        int times = 1;

        String email = "testEmail";

        SenderCriteriaType senderCriteriaType = initCriteria(null, null, email, null, null);
        Sender domainSender = initDomainSender(id);
        SenderType sender = initTestSender(id);

        when(mapper.map(domainSender, SenderType.class)).thenReturn(sender);
        when(senderDAOImpl.findSenderByEmail(email)).thenReturn(domainSender);

        SenderType result = archiveService.findSender((senderCriteriaType));

        assertTrue(result.getId().longValue() == id);
        verify(senderDAOImpl, times(times)).findSenderByEmail(email);
        verify(mapper, times(times)).map(domainSender, SenderType.class);
        verifyNoMoreInteractions(senderDAOImpl);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    public void findSenderByFax() throws Exception {

        long id = 5;
        int times = 1;

        String fax = "testFax";

        SenderCriteriaType senderCriteriaType = initCriteria(null, null, null, fax, null);
        Sender domainSender = initDomainSender(id);
        SenderType sender = initTestSender(id);

        when(mapper.map(domainSender, SenderType.class)).thenReturn(sender);
        when(senderDAOImpl.findSenderByFax(fax)).thenReturn(domainSender);

        SenderType result = archiveService.findSender((senderCriteriaType));

        assertTrue(result.getId().longValue() == id);
        verify(senderDAOImpl, times(times)).findSenderByFax(fax);
        verify(mapper, times(times)).map(domainSender, SenderType.class);
        verifyNoMoreInteractions(senderDAOImpl);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    public void findSenderByNumber() throws Exception {

        long id = 5;
        int times = 1;

        String number = "testNumber";

        SenderCriteriaType senderCriteriaType = initCriteria(null, null, null, null, number);
        Sender domainSender = initDomainSender(id);
        SenderType sender = initTestSender(id);

        when(mapper.map(domainSender, SenderType.class)).thenReturn(sender);
        when(senderDAOImpl.findSenderByPhoneNumber(number)).thenReturn(domainSender);

        SenderType result = archiveService.findSender((senderCriteriaType));

        assertTrue(result.getId().longValue() == id);
        verify(senderDAOImpl, times(times)).findSenderByPhoneNumber(number);
        verify(mapper, times(times)).map(domainSender, SenderType.class);
        verifyNoMoreInteractions(senderDAOImpl);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    public void createSender() throws Exception {

        long id = 5;
        int times = 1;

        by.epam.rafalovich.archiveservice.entity.Sender domainSender = initDomainSender(id);
        SenderType sender = initTestSender(id);

        when(mapper.map(sender, Sender.class)).thenReturn(domainSender);

        archiveService.createSender(sender);

        verify(senderDAOImpl, times(times)).create(domainSender);
        verify(mapper, times(times)).map(sender, Sender.class);
        verifyNoMoreInteractions(senderDAOImpl);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    public void searchRecords() throws Exception {

        int times = 1;
        int yearsAgo = 2;
        long senderId = 5;
        long recipientId = 3;
        long recordId = 1;
        String recipientContact = "testContact";
        String startDateTime = "2015-03-27T09:00:00";
        String endDateTime = "2017-03-27T09:00:00";

        OperationType operationType = OperationType.RESERVATION_CANCELLATION;
        XMLGregorianCalendar start = DatatypeFactory.newInstance().newXMLGregorianCalendar(startDateTime);
        XMLGregorianCalendar end = DatatypeFactory.newInstance().newXMLGregorianCalendar(endDateTime);

        //creating criteriaType
        RecordCriteriaType criteriaType = new RecordCriteriaType();
        criteriaType.setSenderId(BigInteger.valueOf(senderId));
        criteriaType.setRecipientContact(recipientContact);
        criteriaType.setOperationType(operationType);
        criteriaType.setStartDateTime(start);
        criteriaType.setEndDateTime(end);

        //creating collection of communicationRecordType
        CommunicationRecordType record = new CommunicationRecordType();
        record.setId(BigInteger.valueOf(recordId));
        Collection<CommunicationRecordType> records = new ArrayList<>();
        records.add(record);

        Operation domainType = Operation.RESERVATION_CANCELLATION;
        LocalDateTime startDT = LocalDateTime.now().minusYears(yearsAgo);
        LocalDateTime endDT = LocalDateTime.now();

        //creating criteria
        RecordCriteria criteria = new RecordCriteria();
        criteria.setSenderId(senderId);
        criteria.setOperationType(domainType);
        criteria.setStartDateTime(startDT);
        criteria.setEndDateTime(endDT);

        //creating recipient
        Recipient domainRecipient = new Recipient();
        domainRecipient.setRecipientId(recipientId);
        domainRecipient.setRecipientContact(recipientContact);

        //creating collection of communicationRecord
        CommunicationRecord domainRecord = new CommunicationRecord();
        domainRecord.setRecordId(recordId);
        domainRecord.setRecipient(domainRecipient);
        Collection<CommunicationRecord> domainRecords = new ArrayList<>();
        domainRecords.add(domainRecord);

        when ( mapper.map(criteriaType, RecordCriteria.class) ).thenReturn( criteria );


        when ( recipientDAO.findRecipientByContact(recipientContact) ).thenReturn( domainRecipient );
        when ( recordDAO.findRecords(criteria) ).thenReturn( domainRecords );

        Archive archive = archiveService.searchRecords(criteriaType);

        assertTrue(archive.getRecord().size() == domainRecords.size());
        verify(recordDAO, times(times)).findRecords(criteria);
        verify(recipientDAO, times(times)).findRecipientByContact(recipientContact);
        verifyNoMoreInteractions( recordDAO );
        verifyNoMoreInteractions( recipientDAO );
    }
}