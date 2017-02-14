package by.epam.rafalovich.archiveservice.dao.impl;

import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.archiveservice.entity.Sender;
import by.epam.rafalovich.archiveservice.entity.SenderInfo;
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

import static org.junit.Assert.*;
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
public class SenderDAOImplTest {

   @Autowired
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
    @DatabaseSetup(value= "/db/sender/create.xml", type = DatabaseOperation.CLEAN_INSERT)
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
    @DatabaseSetup(value= "/db/sender/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findById() throws Exception {

        long id = 5L;

        Sender result = senderDAOImpl.findById(id);
        assertPropertyLenientEquals("senderName", "Company5", result);
        assertPropertyLenientEquals("info.senderEmail","company5@gmail.com", result);
        assertPropertyLenientEquals("info.senderFax", "8848391", result);
        assertPropertyLenientEquals("info.senderPhoneNumber", "+37529456340", result);
    }

    @Test
    @DatabaseSetup(value= "/db/sender/default.xml", type = DatabaseOperation.CLEAN_INSERT)
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
    @DatabaseSetup(value= "/db/sender/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void delete() throws Exception {

        long id = 1L;

        Sender sender = senderDAOImpl.findById(id);
        assertNotNull(sender);
        senderDAOImpl.delete(sender);
        assertNull(senderDAOImpl.findById(id));
    }

    @Test
    @DatabaseSetup(value= "/db/sender/find-all.xml", type = DatabaseOperation.CLEAN_INSERT)
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
    @DatabaseSetup(value= "/db/sender/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findSenderBySenderName() throws Exception {

        String name = "Company5";

        Sender result = senderDAOImpl.findSenderBySenderName(name);
        assertPropertyLenientEquals("senderId", 5L, result);
        assertPropertyLenientEquals("info.senderEmail","company5@gmail.com", result);
        assertPropertyLenientEquals("info.senderFax", "8848391", result);
        assertPropertyLenientEquals("info.senderPhoneNumber", "+37529456340", result);
    }

    @Test
    @DatabaseSetup(value= "/db/sender/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findSenderByPhoneNumber() throws Exception {

        String phoneNumber = "+37529456340";

        Sender result = senderDAOImpl.findSenderByPhoneNumber(phoneNumber);
        assertPropertyLenientEquals("senderName", "Company5", result);
        assertPropertyLenientEquals("senderId", 5L, result);
        assertPropertyLenientEquals("info.senderEmail","company5@gmail.com", result);
        assertPropertyLenientEquals("info.senderFax", "8848391", result);
    }

    @Test
    @DatabaseSetup(value= "/db/sender/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findSenderByFax() throws Exception {

        String fax = "8848391";

        Sender result = senderDAOImpl.findSenderByFax(fax);
        assertPropertyLenientEquals("senderName", "Company5", result);
        assertPropertyLenientEquals("senderId", 5L, result);
        assertPropertyLenientEquals("info.senderEmail","company5@gmail.com", result);
        assertPropertyLenientEquals("info.senderPhoneNumber", "+37529456340", result);
    }

    @Test
    @DatabaseSetup(value= "/db/sender/default.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void findSenderByEmail() throws Exception {

        String email = "company5@gmail.com";

        Sender result = senderDAOImpl.findSenderByEmail(email);
        assertPropertyLenientEquals("senderName", "Company5", result);
        assertPropertyLenientEquals("senderId", 5L, result);
        assertPropertyLenientEquals("info.senderFax", "8848391", result);
        assertPropertyLenientEquals("info.senderPhoneNumber", "+37529456340", result);
    }

}