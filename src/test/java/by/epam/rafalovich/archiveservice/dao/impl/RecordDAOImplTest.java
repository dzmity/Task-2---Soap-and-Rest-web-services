package by.epam.rafalovich.archiveservice.dao.impl;


import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.entity.*;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.datasetloadstrategy.impl.CleanInsertLoadStrategy;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.unitils.reflectionassert.ReflectionAssert.assertPropertyLenientEquals;

/**
 * Created by Dzmitry_Rafalovich on 1/26/2017.
 */
@SpringApplicationContext(value = "spring/config/testbeans.xml")
@DataSet(value = "db/record/default.xml", loadStrategy = CleanInsertLoadStrategy.class)
public class RecordDAOImplTest extends UnitilsJUnit4 {

    @SpringBeanByName
    RecordDAO recordDAOImpl;

    private CommunicationRecord initRecord() {

        OperationType type = OperationType.RESERVATION_CANCELLATION;
        Long recipientId = 1L;
        CommunicationChannel channel = CommunicationChannel.SMS;
        String recipientContact = "+375336012781";
        String senderName = "Company1";
        Long senderId = 1L;
        String number = "+375294567812";
        String fax = "554655";
        String email = "company20@gmail.com";

        CommunicationRecord record = new CommunicationRecord();
        record.setOperationType(type);
        record.setDateTime(LocalDateTime.now());

        Recipient recipient = new Recipient();
        recipient.setRecipientId(recipientId);
        recipient.setChanel(channel);
        recipient.setRecipientContact(recipientContact);

        record.setRecipient(recipient);

        Sender sender = new Sender();
        sender.setSenderName(senderName);
        sender.setSenderId(senderId);
        SenderInfo info = new SenderInfo();
        info.setId(senderId);
        info.setSender(sender);
        info.setSenderPhoneNumber(number);
        info.setSenderFax(fax);
        info.setSenderEmail(email);
        sender.setInfo(info);
        sender.setInfo(info);

        record.setSender(sender);

        return record;
    }

    @Test
    public void create() throws Exception {

        CommunicationRecord record = initRecord();
        recordDAOImpl.create(record);

        List<CommunicationRecord> result = recordDAOImpl.findAll();
        assertPropertyLenientEquals("operationType", Arrays.asList(OperationType.RESERVATION_CHANGING,
                OperationType.RESERVATION_CANCELLATION), result);
    }

    @Test
    public void findById() throws Exception {

        Long id = 1L;

        CommunicationRecord result = recordDAOImpl.findById(id);
        assertPropertyLenientEquals("operationType", OperationType.RESERVATION_CHANGING, result);
        assertPropertyLenientEquals("sender.senderId", 2L, result);
        assertPropertyLenientEquals("recipient.recipientId", 2L, result);
    }

    @Test
    public void update() throws Exception {

        Long id = 1L;
        CommunicationChannel channel = CommunicationChannel.EMAIL;
        String email = "qwerty123@gmail.com";

        CommunicationRecord record = recordDAOImpl.findById(id);

        Recipient recipient = new Recipient();
        recipient.setChanel(channel);
        recipient.setRecipientContact(email);

        record.setRecipient(recipient);
        recordDAOImpl.update(record);

        CommunicationRecord result = recordDAOImpl.findById(id);
        assertEquals(result.getDateTime(), record.getDateTime());
        assertEquals(result.getSender().getInfo(), record.getSender().getInfo());
        assertEquals(result.getRecipient().getChanel(), record.getRecipient().getChanel());
        assertEquals(result.getRecipient().getRecipientContact(), record.getRecipient().getRecipientContact());
    }

    @Test
    public void delete() throws Exception {

        Long id = 1L;

        CommunicationRecord record = recordDAOImpl.findById(id);
        recordDAOImpl.delete(record);
        assertTrue(recordDAOImpl.findAll().size() == 0);
    }

    @Test
    public void findAll() throws Exception {

        List<CommunicationRecord> result = recordDAOImpl.findAll();
        assertPropertyLenientEquals("operationType", Arrays.asList(OperationType.RESERVATION_CHANGING), result);
        assertPropertyLenientEquals("sender.senderId", Arrays.asList(2L), result);
        assertPropertyLenientEquals("recipient.recipientId", Arrays.asList(2L), result);
    }

    @Test
    public void findRecordsBySender() throws Exception {

        Long id = 2L;

        List<CommunicationRecord> result = (List<CommunicationRecord>) recordDAOImpl.findRecordsBySender(2L);
        assertPropertyLenientEquals("operationType", Arrays.asList(OperationType.RESERVATION_CHANGING), result);
        assertPropertyLenientEquals("recipient.recipientId", Arrays.asList(2L), result);
    }

    @Test
    public void findRecordsByRecipient() throws Exception {

        Long id = 2L;

        List<CommunicationRecord> result = (List<CommunicationRecord>) recordDAOImpl.findRecordsByRecipient(2L);
        assertPropertyLenientEquals("operationType", Arrays.asList(OperationType.RESERVATION_CHANGING), result);
        assertPropertyLenientEquals("sender.senderId", Arrays.asList(2L), result);
    }

}