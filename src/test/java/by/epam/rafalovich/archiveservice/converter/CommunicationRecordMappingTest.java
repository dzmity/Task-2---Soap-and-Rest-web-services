package by.epam.rafalovich.archiveservice.converter;

import by.epam.rafalovich.archiveservice.*;
import by.epam.rafalovich.archiveservice.entity.*;
import org.dozer.DozerBeanMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.unitils.reflectionassert.ReflectionAssert.assertPropertyLenientEquals;

/**
 * Created by Dzmitry_Rafalovich on 2/8/2017.
 */
public class CommunicationRecordMappingTest {

    private static final String PATH = "mapping/dozer_mapping.xml";

    private static DozerBeanMapper mapper;

    @BeforeClass
    public static void before() throws Exception {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList(PATH));
    }

    @Test
    public void testCommunicationRecordTypeMapping() throws DatatypeConfigurationException {

        long id = 11L;
        String name = "hotel_california";
        String fax = "12345";
        String email = "qwerty@gmail.com";
        String recipientNumber = "+375336012781";
        String number = "+375336012781";
        String dateTime = "2002-05-30T09:00:00";

        OperationType confirmation = OperationType.RESERVATION_CONFIRMATION;
        Operation operationType = Operation.RESERVATION_CONFIRMATION;
        CommunicationChannelType sms = CommunicationChannelType.SMS;
        CommunicationChannel channel = CommunicationChannel.SMS;

        int year = 2002;
        int month = 5;
        int day = 30;
        int hour = 9;
        int minute = 0;
        int second = 0;

        CommunicationRecordType dto = new CommunicationRecordType();
        dto.setId(BigInteger.valueOf(id));
        dto.setOperation(confirmation);

        RecipientType recipient = new RecipientType();
        recipient.setId(BigInteger.valueOf(id));
        recipient.setChannelType(sms);
        recipient.setRecipientContact(recipientNumber);

        dto.setRecipient(recipient);

        SenderType sender = new SenderType();
        sender.setSenderName(name);
        sender.setId(BigInteger.valueOf(id));

        SenderInfoType info = new SenderInfoType();
        info.setId(BigInteger.valueOf(id));
        info.setPhoneNumber(number);
        info.setFax(fax);
        info.setEmail(email);

        sender.setSenderInfo(info);

        dto.setSender(sender);
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTime);
        dto.setDateTime(xcal);

        CommunicationRecord record = mapper.map(dto, CommunicationRecord.class);

        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute, second);

        assertPropertyLenientEquals("dateTime", localDateTime, record);
        assertPropertyLenientEquals("operationType", operationType, record);
        assertPropertyLenientEquals("sender.senderName", name, record);
        assertPropertyLenientEquals("sender.info.senderFax", fax, record);
        assertPropertyLenientEquals("recipient.chanel", channel, record);
        assertPropertyLenientEquals("recipient.recipientContact", recipientNumber, record);
    }

    @Test
    public void testCommunicationRecordMapping() throws DatatypeConfigurationException {

        long id = 11L;
        String name = "hotel_california";
        String fax = "12345";
        String email = "qwerty@gmail.com";
        String recipientNumber = "+375336012781";
        String number = "+375336012781";
        String dateTime = "2002-05-30T09:00:00";

        OperationType confirmation = OperationType.RESERVATION_CONFIRMATION;
        Operation operationType = Operation.RESERVATION_CONFIRMATION;
        CommunicationChannelType sms = CommunicationChannelType.SMS;
        CommunicationChannel channel = CommunicationChannel.SMS;

        int year = 2002;
        int month = 5;
        int day = 30;
        int hour = 9;
        int minute = 0;
        int second = 0;

        CommunicationRecord record = new CommunicationRecord();
        record.setRecordId(id);
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
        record.setDateTime(localDateTime);
        record.setOperationType(operationType);

        Recipient recipient = new Recipient();
        recipient.setRecipientId(id);
        recipient.setChanel(channel);
        recipient.setRecipientContact(recipientNumber);

        record.setRecipient(recipient);

        Sender sender = new Sender();
        sender.setSenderId(id);
        sender.setSenderName(name);

        SenderInfo info = new SenderInfo();
        info.setId(id);
        info.setSenderFax(fax);
        info.setSenderEmail(email);
        info.setSenderPhoneNumber(number);

        sender.setInfo(info);
        record.setSender(sender);

        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTime);

        CommunicationRecordType dto = mapper.map(record, CommunicationRecordType.class);

        assertPropertyLenientEquals("dateTime", xcal, dto);
        assertPropertyLenientEquals("operation", confirmation, dto);
        assertPropertyLenientEquals("sender.senderName", name, dto);
        assertPropertyLenientEquals("sender.senderInfo.fax", fax, dto);
        assertPropertyLenientEquals("recipient.channelType", sms, dto);
        assertPropertyLenientEquals("recipient.recipientContact", recipientNumber, dto);
    }
}
