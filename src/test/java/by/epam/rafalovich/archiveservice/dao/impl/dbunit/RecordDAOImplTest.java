package by.epam.rafalovich.archiveservice.dao.impl.dbunit;

import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.entity.*;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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
    @DatabaseSetup(value= "/db/record/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void create() throws Exception {

        CommunicationRecord record = initRecord();
        recordDAOImpl.create(record);

        List<CommunicationRecord> result = recordDAOImpl.findAll();
        assertPropertyLenientEquals("operationType", Arrays.asList(OperationType.RESERVATION_CHANGING,
                OperationType.RESERVATION_CANCELLATION), result);
    }

    @Test
    @DatabaseSetup(value= "/db/record/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findById() throws Exception {

        Long id = 1L;

        CommunicationRecord result = recordDAOImpl.findById(id);
        assertPropertyLenientEquals("operationType", OperationType.RESERVATION_CHANGING, result);
        assertPropertyLenientEquals("sender.senderId", 2L, result);
        assertPropertyLenientEquals("recipient.recipientId", 2L, result);
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

        CommunicationRecord record = recordDAOImpl.findById(id);
        recordDAOImpl.delete(record);
        assertTrue(recordDAOImpl.findAll().size() == 0);
    }

    @Test
    @DatabaseSetup(value= "/db/record/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findAll() throws Exception {

        List<CommunicationRecord> result = recordDAOImpl.findAll();
        assertPropertyLenientEquals("operationType", Arrays.asList(OperationType.RESERVATION_CHANGING), result);
        assertPropertyLenientEquals("sender.senderId", Arrays.asList(2L), result);
        assertPropertyLenientEquals("recipient.recipientId", Arrays.asList(2L), result);
    }

    @Test
    @DatabaseSetup(value= "/db/record/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findRecordsBySender() throws Exception {

        Long id = 2L;

        List<CommunicationRecord> result = (List<CommunicationRecord>) recordDAOImpl.findRecordsBySender(2L);
        assertPropertyLenientEquals("operationType", Arrays.asList(OperationType.RESERVATION_CHANGING), result);
        assertPropertyLenientEquals("recipient.recipientId", Arrays.asList(2L), result);
    }

    @Test
    @DatabaseSetup(value= "/db/record/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findRecordsByRecipient() throws Exception {

        Long id = 2L;

        List<CommunicationRecord> result = (List<CommunicationRecord>) recordDAOImpl.findRecordsByRecipient(2L);
        assertPropertyLenientEquals("operationType", Arrays.asList(OperationType.RESERVATION_CHANGING), result);
        assertPropertyLenientEquals("sender.senderId", Arrays.asList(2L), result);
    }

}