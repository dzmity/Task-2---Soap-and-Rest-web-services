package by.epam.rafalovich.archiveservice;

import by.epam.rafalovich.archiveservice.dao.GenericDAO;
import by.epam.rafalovich.archiveservice.dao.RecordDAO;
import by.epam.rafalovich.archiveservice.dao.SenderDAO;
import by.epam.rafalovich.archiveservice.dao.impl.SenderDAOImpl;
import by.epam.rafalovich.archiveservice.entity.SenderInfo;
import by.epam.rafalovich.archiveservice.entity.OperationType;
import by.epam.rafalovich.archiveservice.entity.Sender;
import by.epam.rafalovich.archiveservice.exception.DAOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created by Dzmitry_Rafalovich on 1/31/2017.
 */
public class Start {

    public static void main(String[] args) throws DAOException {
        ApplicationContext appContext =
                new ClassPathXmlApplicationContext("classpath:cxf/cxf-servlet-rest.xml");

        Arrays.stream(appContext.getBeanDefinitionNames()).forEach(System.out::println);
        //Session session = ((SessionFactory) appContext.getBean("sessionFactory")).getCurrentSession();

        GenericDAO<Sender> senderDAO = (SenderDAO) appContext.getBean("senderDAOImpl");
        Sender sender = new Sender();
        sender.setSenderName("crown plaza 2");
        SenderInfo info = new SenderInfo();
        info.setSenderEmail("plaza@gmail.com");
        info.setSenderFax("1234567");
        info.setSenderPhoneNumber("+37533345689");
        info.setId(123L);
        info.setSender(sender);
        sender.setInfo(info);
        senderDAO.create(sender);
        //GenericDAO<CommunicationRecord> recordDAO = new RecordDAOImpl();
        /*RecordDAO recordDAO =(RecordDAO) appContext.getBean("recordDAOImpl");
        CommunicationRecord record = new CommunicationRecord();
        record.setOperationType(OperationType.RESERVATION_CANCELLATION);
        record.setDateTime(LocalDateTime.now());


        Recipient recipient = new Recipient();
        recipient.setChanel(CommunicationChannel.SMS);
        recipient.setRecipientContact("80293456789");

        record.setRecipient(recipient);

        Sender sender = new Sender();
        sender.setSenderName("crown plaza 2");

        SenderInfo info = new SenderInfo();
        info.setSenderEmail("plaza@gmail.com");
        info.setSenderFax("1234567");
        info.setSenderPhoneNumber("+37533345689");
        info.setSender(sender);
        sender.setInfo(info);
        record.setSender(sender);
        recordDAO.create(record);*/
    }
}
