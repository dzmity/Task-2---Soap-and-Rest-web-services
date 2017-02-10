package by.epam.rafalovich.archiveservice.provider.rest;

import by.epam.rafalovich.archiveservice.*;
import by.epam.rafalovich.archiveservice.OperationType;
import by.epam.rafalovich.archiveservice.dao.RecipientDAO;
import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.entity.*;
import by.epam.rafalovich.archiveservice.provider.rest.impl.ArchiveServiceImpl;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Dzmitry_Rafalovich on 2/8/2017.
 */
public class ArchiveServiceImplTest {

    @Mock
    private RecipientDAO recipientDAO;

    @Mock
    private RecordDAO recordDAO;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private ArchiveServiceImpl archiveService;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);    }

    @Test
    public void findRecords() throws Exception {

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

        Record record = new Record();
        record.setId(BigInteger.valueOf(recordId));

        Collection<Record> records = new ArrayList<>();
        records.add(record);

        Operation domainType =
                Operation.RESERVATION_CANCELLATION;
        LocalDateTime startDT = LocalDateTime.now().minusYears(yearsAgo);
        LocalDateTime endDT = LocalDateTime.now();

        by.epam.rafalovich.archiveservice.entity.Recipient domainRecipient = new by.epam.rafalovich.archiveservice.entity.Recipient();
        domainRecipient.setRecipientId(recipientId);
        domainRecipient.setRecipientContact(recipientContact);

        CommunicationRecord domainRecord = new CommunicationRecord();
        domainRecord.setRecordId(recordId);
        domainRecord.setRecipient(domainRecipient);

        Collection<CommunicationRecord> domainRecords = new ArrayList<>();
        domainRecords.add(domainRecord);

        when ( mapper.map(start, LocalDateTime.class) ).thenReturn( startDT );
        when ( mapper.map(end, LocalDateTime.class) ).thenReturn( endDT );
        when ( mapper.map(operationType, Operation.class) ).thenReturn( domainType );
        when ( mapper.map(domainRecord, Record.class) ).thenReturn( record );
        when ( recipientDAO.findRecipientByContact(recipientContact) ).thenReturn( domainRecipient );
        when ( recordDAO.findRecords(senderId, recipientId, startDT, endDT, domainType) ).thenReturn( domainRecords );

        Archive archive = archiveService.findRecords(BigInteger.valueOf(senderId), recipientContact, start, end, operationType);

        assertTrue(archive.getRecord().size() == domainRecords.size());
        verify(recordDAO, times(1)).findRecords(senderId, recipientId, startDT, endDT, domainType);
        verify(recipientDAO, times(1)).findRecipientByContact(recipientContact);
        verifyNoMoreInteractions( recordDAO );
        verifyNoMoreInteractions( recipientDAO );
    }

}