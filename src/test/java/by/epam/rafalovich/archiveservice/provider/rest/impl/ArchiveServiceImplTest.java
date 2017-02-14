package by.epam.rafalovich.archiveservice.provider.rest.impl;

import by.epam.rafalovich.archiveservice.dao.RecipientDAO;
import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.entity.CommunicationRecord;
import by.epam.rafalovich.archiveservice.entity.Recipient;
import by.epam.rafalovich.archiveservice.entity.RecordCriteria;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Dzmitry_Rafalovich on 2/13/2017.
 */
public class ArchiveServiceImplTest {

    @Mock
    private RecipientDAO recipientDAO;

    @Mock
    private RecordDAO recordDAO;

    @InjectMocks
    private ArchiveServiceImpl archiveService;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
    }

    private CommunicationRecord initRecord(long id) {

        CommunicationRecord record = new CommunicationRecord();
        record.setRecordId(id);
        record.setDateTime(LocalDateTime.now().plusDays(id));

        return record;
    }

    @Test
    public void findRecords() throws Exception {

        long count = 3;
        int times = 1;

        RecordCriteria recordCriteria = new RecordCriteria();
        recordCriteria.setSenderId(null);
        recordCriteria.setOperationType(null);
        recordCriteria.setRecipientId(null);
        recordCriteria.setStartDateTime(null);
        recordCriteria.setEndDateTime(null);

        List<CommunicationRecord> records = new ArrayList<>();

        for (long i = 0; i < count; i++) {
            records.add(initRecord(i));
        }

        when ( recordDAO.findRecords(recordCriteria) ).thenReturn( records );

        List<CommunicationRecord> result = archiveService.findRecords(null, null, null, null, null);

        assertTrue(result.size() == count);
        verify(recordDAO, times(times)).findRecords(recordCriteria);

        verifyNoMoreInteractions( recordDAO );
        verifyNoMoreInteractions( recipientDAO );
    }

    @Test
    public void findRecordsWithSender() throws Exception {

        long count = 3;
        int times = 1;
        long recipientId = 2;

        String contact = "testContact";

        Recipient recipient = new Recipient();
        recipient.setRecipientContact(contact);
        recipient.setRecipientId(recipientId);

        RecordCriteria recordCriteria = new RecordCriteria();
        recordCriteria.setSenderId(null);
        recordCriteria.setOperationType(null);
        recordCriteria.setRecipientId(recipientId);
        recordCriteria.setStartDateTime(null);
        recordCriteria.setEndDateTime(null);

        List<CommunicationRecord> records = new ArrayList<>();

        for (long i = 0; i < count; i++) {
            records.add(initRecord(i));
        }

        when(recipientDAO.findRecipientByContact(contact)).thenReturn(recipient);
        when(recordDAO.findRecords(recordCriteria)).thenReturn(records);

        List<CommunicationRecord> result = archiveService.findRecords(null, contact, null, null, null);

        assertTrue(result.size() == count);
        verify(recordDAO, times(times)).findRecords(recordCriteria);
        verify(recipientDAO, times(times)).findRecipientByContact(contact);

        verifyNoMoreInteractions(recordDAO);
        verifyNoMoreInteractions(recipientDAO);
    }
}