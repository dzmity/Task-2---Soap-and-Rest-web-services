package by.epam.rafalovich.archiveservice.dao.impl;

import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.entity.*;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.unitils.reflectionassert.ReflectionAssert.assertPropertyLenientEquals;

/**
 * Created by Dzmitry_Rafalovich on 1/26/2017.
 */
@RunWith( SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/config/testbeans.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class RecordDAOImplTest {

    @Autowired
    RecordDAO recordDAOImpl;

    private CommunicationRecord initRecord() {

        Operation type = Operation.RESERVATION_CANCELLATION;
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
    @DatabaseSetup(value= "/db/record/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void create() throws Exception {

        CommunicationRecord record = initRecord();

        recordDAOImpl.create(record);

        List<CommunicationRecord> result = recordDAOImpl.findAll();
        assertPropertyLenientEquals("operationType", Arrays.asList(Operation.RESERVATION_CHANGING,
                Operation.RESERVATION_CANCELLATION), result);
    }

    @Test
    @DatabaseSetup(value= "/db/record/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findById() throws Exception {

        Long id = 1L;
        long senderId = 2L;
        long recipientId = 2L;

        CommunicationRecord result = recordDAOImpl.findById(id);
        assertPropertyLenientEquals("operationType", Operation.RESERVATION_CHANGING, result);
        assertPropertyLenientEquals("sender.senderId", senderId, result);
        assertPropertyLenientEquals("recipient.recipientId", recipientId, result);
    }

    @Test
    @DatabaseSetup(value= "/db/record/default.xml", type = DatabaseOperation.CLEAN_INSERT)
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
    @DatabaseSetup(value= "/db/record/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void delete() throws Exception {

        Long id = 1L;
        int expectedSize = 0;

        CommunicationRecord record = recordDAOImpl.findById(id);
        recordDAOImpl.delete(record);
        assertTrue(recordDAOImpl.findAll().size() == expectedSize);
    }

    @Test
    @DatabaseSetup(value= "/db/record/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findAll() throws Exception {

        long senderId = 2L;
        long recipientId = 2L;

        List<CommunicationRecord> result = recordDAOImpl.findAll();
        assertPropertyLenientEquals("operationType", Arrays.asList(Operation.RESERVATION_CHANGING), result);
        assertPropertyLenientEquals("sender.senderId", Arrays.asList(senderId), result);
        assertPropertyLenientEquals("recipient.recipientId", Arrays.asList(recipientId), result);
    }

    @Test
    @DatabaseSetup(value= "/db/record/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findByCriteria() throws Exception {

        Long recordId = 1L;
        Long senderId = 2L;
        Long recipientId = 2L;
        int yearsBeforeCount = 3;
        int expectedCount = 1;

        Operation operation = Operation.RESERVATION_CHANGING;
        LocalDateTime startDateTime = LocalDateTime.now().minusYears(yearsBeforeCount);
        LocalDateTime endDateTime = LocalDateTime.now();


        RecordCriteria recordCriteria = new RecordCriteria();
        recordCriteria.setSenderId(senderId);
        recordCriteria.setOperationType(operation);
        recordCriteria.setRecipientId(recipientId);
        recordCriteria.setStartDateTime(startDateTime);
        recordCriteria.setEndDateTime(endDateTime);

        Collection<CommunicationRecord> results = recordDAOImpl.findRecords(recordCriteria);

        assertTrue(results.size() == expectedCount);
        assertPropertyLenientEquals("recordId", Arrays.asList(recordId), results);
        assertPropertyLenientEquals("operationType", Arrays.asList(operation), results);
        assertPropertyLenientEquals("sender.senderId", Arrays.asList(senderId), results);
        assertPropertyLenientEquals("recipient.recipientId", Arrays.asList(recipientId), results);
    }

    @Test
    @DatabaseSetup(value= "/db/record/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findRecordCount() throws Exception {

        long expectedCount = 1;
        long result = recordDAOImpl.findRecordCount();
        assertTrue(expectedCount == result);
    }


}