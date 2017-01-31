package by.epam.rafalovich.archiveservice.dao.impl;


import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.archiveservice.entity.Sender;
import by.epam.rafalovich.archiveservice.entity.SenderInfo;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.datasetloadstrategy.impl.CleanInsertLoadStrategy;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.unitils.reflectionassert.ReflectionAssert.assertPropertyLenientEquals;

/**
 * Created by Dzmitry_Rafalovich on 1/26/2017.
 */
@SpringApplicationContext(value = "spring/config/testbeans.xml")
@DataSet(value = "db/sender/default.xml", loadStrategy = CleanInsertLoadStrategy.class)
public class SenderDAOImplTest extends UnitilsJUnit4 {

    @SpringBeanByName
    private SenderDAO senderDAOImpl;

    private Sender initSender() {


        String name = "Company20";
        String number = "+375294567812";
        String fax = "554655";
        String email = "company20@gmail.com";

        Sender sender = new Sender();
        sender.setSenderName(name);
        SenderInfo info = new SenderInfo();
        info.setSender(sender);
        info.setSenderPhoneNumber(number);
        info.setSenderFax(fax);
        info.setSenderEmail(email);
        sender.setInfo(info);
        return sender;
    }

    @Test
    @DataSet(value = "db/sender/create.xml", loadStrategy = CleanInsertLoadStrategy.class)
    public void create() throws Exception {

        Sender sender = initSender();
        senderDAOImpl.create(sender);

        List<Sender> result = senderDAOImpl.findAll();
        assertPropertyLenientEquals("senderName", Arrays.asList("Company1", "Company2", "Company20"), result);
        assertPropertyLenientEquals("info.senderEmail", Arrays.asList("company1@gmail.com",
                "company2@gmail.com", "company20@gmail.com"), result);
        assertPropertyLenientEquals("info.senderFax", Arrays.asList("8848396","8848395", "554655"), result);
        assertPropertyLenientEquals("info.senderPhoneNumber", Arrays.asList("+37529456346","+37529456347",
                "+375294567812"), result);
    }

    @Test
    public void findById() throws Exception {

        long id = 5L;

        Sender result = senderDAOImpl.findById(id);
        assertPropertyLenientEquals("senderName", "Company5", result);
        assertPropertyLenientEquals("info.senderEmail","company5@gmail.com", result);
        assertPropertyLenientEquals("info.senderFax", "8848391", result);
        assertPropertyLenientEquals("info.senderPhoneNumber", "+37529456340", result);
    }

    @Test
    public void update() throws Exception {

        long id = 4L;

        Sender sender = initSender();
        sender.setSenderId(id);
        sender.getInfo().setId(id);
        senderDAOImpl.update(sender);
        Sender result = senderDAOImpl.findById(id);
        assertEquals(sender, result);
    }

    @Test
    public void delete() throws Exception {

        long id = 1L;

        Sender sender = senderDAOImpl.findById(id);
        assertNotNull(sender);
        senderDAOImpl.delete(sender);
        assertNull(senderDAOImpl.findById(id));
    }

    @Test
    @DataSet(value = "db/sender/find-all.xml", loadStrategy = CleanInsertLoadStrategy.class)
    public void findAll() throws Exception {

        List<Sender> result = senderDAOImpl.findAll();
        assertPropertyLenientEquals("senderName", Arrays.asList("Company1", "Company2", "Company3"), result);
        assertPropertyLenientEquals("info.senderEmail", Arrays.asList("company1@gmail.com",
                "company2@gmail.com", "company3@gmail.com"), result);
        assertPropertyLenientEquals("info.senderFax", Arrays.asList("8848396","8848395", "8848394"), result);
        assertPropertyLenientEquals("info.senderPhoneNumber", Arrays.asList("+37529456346",
                "+37529456347", "+37529456348"), result);
    }

    @Test
    public void findSenderBySenderName() throws Exception {

        String name = "Company5";

        Sender result = senderDAOImpl.findSenderBySenderName(name);
        assertPropertyLenientEquals("senderId", 5L, result);
        assertPropertyLenientEquals("info.senderEmail","company5@gmail.com", result);
        assertPropertyLenientEquals("info.senderFax", "8848391", result);
        assertPropertyLenientEquals("info.senderPhoneNumber", "+37529456340", result);
    }

    @Test
    public void findSenderByPhoneNumber() throws Exception {

        String phoneNumber = "+37529456340";

        Sender result = senderDAOImpl.findSenderByPhoneNumber(phoneNumber);
        assertPropertyLenientEquals("senderName", "Company5", result);
        assertPropertyLenientEquals("senderId", 5L, result);
        assertPropertyLenientEquals("info.senderEmail","company5@gmail.com", result);
        assertPropertyLenientEquals("info.senderFax", "8848391", result);
    }

    @Test
    public void findSenderByFax() throws Exception {

        String fax = "8848391";

        Sender result = senderDAOImpl.findSenderByFax(fax);
        assertPropertyLenientEquals("senderName", "Company5", result);
        assertPropertyLenientEquals("senderId", 5L, result);
        assertPropertyLenientEquals("info.senderEmail","company5@gmail.com", result);
        assertPropertyLenientEquals("info.senderPhoneNumber", "+37529456340", result);
    }

    @Test
    public void findSenderByEmail() throws Exception {

        String email = "company5@gmail.com";

        Sender result = senderDAOImpl.findSenderByEmail(email);
        assertPropertyLenientEquals("senderName", "Company5", result);
        assertPropertyLenientEquals("senderId", 5L, result);
        assertPropertyLenientEquals("info.senderFax", "8848391", result);
        assertPropertyLenientEquals("info.senderPhoneNumber", "+37529456340", result);
    }

}