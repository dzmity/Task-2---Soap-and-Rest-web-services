package by.epam.rafalovich.archiveservice;

import by.epam.rafalovich.archiveservice.provider.rest.impl.SenderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Dzmitry_Rafalovich on 1/31/2017.
 */
public class Start {

    public static void main(String[] args) {
        ApplicationContext appContext =
                new ClassPathXmlApplicationContext("classpath:cxf/cxf-servlet-soap.xml");

        Arrays.stream(appContext.getBeanDefinitionNames()).forEach(System.out::println);
        //Session session = ((SessionFactory) appContext.getBean("sessionFactory")).getCurrentSession();

       /* GenericDAO<Sender> senderDAO = (SenderDAO) appContext.getBean("senderDAOImpl");
        Sender sender = new Sender();
        sender.setSenderName("crown plaza 2");
        SenderInfo info = new SenderInfo();
        info.setSenderEmail("plaza@gmail.com");
        info.setSenderFax("1234567");
        info.setSenderPhoneNumber("+37533345689");
        info.setId(123L);
        info.setSender(sender);
        sender.setInfo(info);
        senderDAO.create(sender);*/


       SenderServiceImpl senderService = (SenderServiceImpl) appContext.getBean("senderService");
      // Collection<by.epam.rafalovich.archiveservice.Sender> senders = senderService.findAllSenders().getSender();
       // System.out.println(senders.size());


        //GenericDAO<CommunicationRecord> recordDAO = new RecordDAOImpl();

        /*RecordDAO recordDAO =(RecordDAO) appContext.getBean("recordDAOImpl");
        Collection<CommunicationRecord> records = recordDAO.findRecords(null, null, null, null, null);
        System.out.println(records.size());*/

        /*CommunicationRecord record = new CommunicationRecord();
        record.setOperationType(Operation.RESERVATION_CANCELLATION);
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
