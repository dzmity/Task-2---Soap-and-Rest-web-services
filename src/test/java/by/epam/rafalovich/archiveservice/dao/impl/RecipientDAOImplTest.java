package by.epam.rafalovich.archiveservice.dao.impl;

import by.epam.rafalovich.archiveservice.dao.RecipientDAO;
import by.epam.rafalovich.archiveservice.entity.CommunicationChannel;
import by.epam.rafalovich.archiveservice.entity.Recipient;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.datasetloadstrategy.impl.CleanInsertLoadStrategy;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.unitils.reflectionassert.ReflectionAssert.assertPropertyLenientEquals;

/**
 * Created by Dzmitry_Rafalovich on 1/25/2017.
 */
@SpringApplicationContext(value = "spring/config/testbeans.xml")
@DataSet(value = "db/recipient/startDB.xml", loadStrategy = CleanInsertLoadStrategy.class)
public class RecipientDAOImplTest extends UnitilsJUnit4 {

    @SpringBeanByName
    private RecipientDAO recipientDAOImpl;

    private Recipient initRecipient() {
        Recipient recipient = new Recipient();
        recipient.setChanel(CommunicationChannel.EMAIL);
        recipient.setRecipientContact("qwerty123@gmail.com");
        return  recipient;
    }

    @Test
    @DataSet(value = "db/recipient/create.xml", loadStrategy = CleanInsertLoadStrategy.class)
    //@ExpectedDataSet(value = "db/recipient/createExpected.xml")
    public void create() throws Exception {

        Recipient recipient = initRecipient();
        recipientDAOImpl.create(recipient);

        List<Recipient> result = recipientDAOImpl.findAll();
        assertPropertyLenientEquals("chanel", Arrays.asList(CommunicationChannel.SMS, CommunicationChannel.EMAIL), result);
        assertPropertyLenientEquals("recipientContact", Arrays.asList("12345","qwerty123@gmail.com"), result);
    }

    @Test
    @DataSet(value = "db/recipient/startDB.xml", loadStrategy = CleanInsertLoadStrategy.class)
    public void findById() throws Exception {

        Recipient result = recipientDAOImpl.findById(1L);
        assertPropertyLenientEquals("recipientContact","12345",result);
    }

    @Test
   // @ExpectedDataSet(value = "db/recipient/updateExpected.xml")
    public void update() throws Exception {

        Recipient recipient = initRecipient();
        recipient.setRecipientId(1L);
        recipientDAOImpl.update(recipient);

        Recipient result = recipientDAOImpl.findById(1L);
        assertEquals(recipient, result);

    }

    @Test
    @DataSet(value = "db/recipient/delete.xml", loadStrategy = CleanInsertLoadStrategy.class)
    public void delete() throws Exception {

        long id = 2;

        Recipient recipient = initRecipient();
        recipient.setRecipientId(id);
        recipientDAOImpl.delete(recipient);

        Recipient result = recipientDAOImpl.findById(id);
        assertNull(result);
    }

    @Test
    public void findAll() throws Exception {

        List<Recipient> results = recipientDAOImpl.findAll();
        assertPropertyLenientEquals("chanel", Arrays.asList(CommunicationChannel.SMS, CommunicationChannel.EMAIL, CommunicationChannel.FAX), results);
        assertPropertyLenientEquals("recipientContact", Arrays.asList("12345", "12345@email.ru","123r45"), results);
    }

    @Test
    public void findRecipientByContact() throws Exception {

        String contact = "12345@email.ru";

        Recipient result = recipientDAOImpl.findRecipientByContact(contact);
        assertPropertyLenientEquals("recipientContact","12345@email.ru",result);
        assertPropertyLenientEquals("recipientId",2L,result);
        assertPropertyLenientEquals("chanel", CommunicationChannel.EMAIL,result);
    }

}