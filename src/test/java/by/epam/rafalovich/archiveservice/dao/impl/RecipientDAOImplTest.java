package by.epam.rafalovich.archiveservice.dao.impl;

import by.epam.rafalovich.archiveservice.dao.RecipientDAO;
import by.epam.rafalovich.archiveservice.entity.CommunicationChannel;
import by.epam.rafalovich.archiveservice.entity.Recipient;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.unitils.reflectionassert.ReflectionAssert.assertPropertyLenientEquals;

/**
 * Created by Dzmitry_Rafalovich on 1/25/2017.
 */
@RunWith( SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/config/testbeans.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class RecipientDAOImplTest  {

    @Autowired
    private RecipientDAO recipientDAOImpl;

    private Recipient initRecipient() {
        Recipient recipient = new Recipient();
        recipient.setChanel(CommunicationChannel.EMAIL);
        recipient.setRecipientContact("qwerty123@gmail.com");
        return  recipient;
    }

    @Test
    @DatabaseSetup(value= "/db/recipient/create.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void create() throws Exception {

        Recipient recipient = initRecipient();
        recipientDAOImpl.create(recipient);

        List<Recipient> result = recipientDAOImpl.findAll();
        assertPropertyLenientEquals("chanel", Arrays.asList(CommunicationChannel.SMS, CommunicationChannel.EMAIL), result);
        assertPropertyLenientEquals("recipientContact", Arrays.asList("12345","qwerty123@gmail.com"), result);
    }

    @Test
    @DatabaseSetup(value= "/db/recipient/startDB.xml")
    public void findById() throws Exception {

        long id = 1L;

        Recipient result = recipientDAOImpl.findById(id);
        assertPropertyLenientEquals("recipientContact","12345",result);
    }

    @Test
    @DatabaseSetup(value= "/db/recipient/startDB.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void update() throws Exception {

        long id = 1L;

        Recipient recipient = initRecipient();
        recipient.setRecipientId(id);
        recipientDAOImpl.update(recipient);

        Recipient result = recipientDAOImpl.findById(id);
        assertEquals(recipient, result);

    }

    @Test
    @DatabaseSetup(value= "/db/recipient/delete.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void delete() throws Exception {

        long id = 2;

        Recipient recipient = initRecipient();
        recipient.setRecipientId(id);
        recipientDAOImpl.delete(recipient);

        Recipient result = recipientDAOImpl.findById(id);
        assertNull(result);
    }

    @Test
    @DatabaseSetup(value= "/db/recipient/startDB.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findAll() throws Exception {

        List<Recipient> results = recipientDAOImpl.findAll();
        assertPropertyLenientEquals("chanel", Arrays.asList(CommunicationChannel.SMS, CommunicationChannel.EMAIL, CommunicationChannel.FAX), results);
        assertPropertyLenientEquals("recipientContact", Arrays.asList("12345", "12345@email.ru","123r45"), results);
    }

    @Test
    @DatabaseSetup(value= "/db/recipient/startDB.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findRecipientByContact() throws Exception {

        String contact = "12345@email.ru";
        long recipientId = 2L;

        Recipient result = recipientDAOImpl.findRecipientByContact(contact);
        assertPropertyLenientEquals("recipientContact","12345@email.ru",result);
        assertPropertyLenientEquals("recipientId",recipientId,result);
        assertPropertyLenientEquals("chanel", CommunicationChannel.EMAIL,result);
    }

}