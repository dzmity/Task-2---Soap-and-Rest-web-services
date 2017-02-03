package by.epam.rafalovich.archiveservice.converter;

import by.epam.rafalovich.archiveservice.*;
import by.epam.rafalovich.archiveservice.entity.CommunicationChannel;
import by.epam.rafalovich.archiveservice.entity.CommunicationRecord;
import org.dozer.DozerBeanMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertPropertyLenientEquals;

/**
 * Created by Dzmitry_Rafalovich on 1/31/2017.
 */
public class MapperTest {

    private static DozerBeanMapper mapper;

    @BeforeClass
    public static void before() throws Exception {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList("mapping/dozer_mapping.xml"));
    }

    @Test
    public void testRecipientMarshalling() {

        long id = 10L;
        String contact = "+375336012781";
        ChannelType type = ChannelType.SMS;
        CommunicationChannel channel = CommunicationChannel.SMS;

        Recipient dto = new Recipient();
        dto.setId(BigInteger.valueOf(id));
        dto.setRecipientContact(contact);
        dto.setChannelType(type);

        by.epam.rafalovich.archiveservice.entity.Recipient domen = new by.epam.rafalovich.archiveservice.entity.Recipient();
        domen.setRecipientId(id);
        domen.setRecipientContact(contact);
        domen.setChanel(channel);

        by.epam.rafalovich.archiveservice.entity.Recipient result = mapper.map(dto, by.epam.rafalovich.archiveservice.entity.Recipient.class);

        assertEquals(result, domen);
    }

    @Test
    public void testRecipientUnmarshalling() {

        long id = 5L;
        String contact = "qwerty@gmail.com";
        ChannelType type = ChannelType.EMAIL;
        CommunicationChannel channel = CommunicationChannel.EMAIL;

        by.epam.rafalovich.archiveservice.entity.Recipient domen = new by.epam.rafalovich.archiveservice.entity.Recipient();
        domen.setRecipientId(id);
        domen.setRecipientContact(contact);
        domen.setChanel(channel);

        by.epam.rafalovich.archiveservice.Recipient result = mapper.map(domen, by.epam.rafalovich.archiveservice.Recipient.class);

        assertPropertyLenientEquals("id", BigInteger.valueOf(id), result);
        assertPropertyLenientEquals("recipientContact", contact, result);
        assertPropertyLenientEquals("channelType", type, result);
    }

    @Test
    public void testSenderInfoMarshalling() {

        long id = 12L;
        String fax = "123";
        String email = "qwerty@gmail.com";
        String number = "+375336012781";

        SenderInfo dto = new SenderInfo();
        dto.setId(BigInteger.valueOf(id));
        dto.setFax(fax);
        dto.setEmail(email);
        dto.setPhoneNumber(number);

        by.epam.rafalovich.archiveservice.entity.SenderInfo domen =
                mapper.map(dto, by.epam.rafalovich.archiveservice.entity.SenderInfo.class);

        assertTrue(domen.getId() == id);
        assertEquals(domen.getSenderFax(), dto.getFax());
        assertEquals(domen.getSenderEmail(), dto.getEmail());
    }

    @Test
    public void testSenderInfoUnmarshalling() {

        long id = 12L;
        String fax = "123";
        String email = "qwerty@gmail.com";
        String number = "+375336012781";

        by.epam.rafalovich.archiveservice.entity.SenderInfo domen = new by.epam.rafalovich.archiveservice.entity.SenderInfo();
        domen.setId(id);
        domen.setSenderEmail(email);
        domen.setSenderFax(fax);
        domen.setSenderPhoneNumber(number);

        SenderInfo dto = mapper.map(domen, by.epam.rafalovich.archiveservice.SenderInfo.class);
        assertSame(dto.getId(), BigInteger.valueOf(id));
        assertEquals(domen.getSenderFax(), dto.getFax());
        assertEquals(domen.getSenderEmail(), dto.getEmail());
    }

    @Test
    public void testSenderMarshalling() {

        long id = 10L;
        String name = "hotel";
        String fax = "123";
        String email = "qwerty@gmail.com";
        String number = "+375336012781";

        Sender dto = new Sender();
        by.epam.rafalovich.archiveservice.entity.Sender domen = new by.epam.rafalovich.archiveservice.entity.Sender();
        domen.setSenderId(id);
        domen.setSenderName(name);

        SenderInfo dtoInfo = new SenderInfo();
        by.epam.rafalovich.archiveservice.entity.SenderInfo domenInfo = new by.epam.rafalovich.archiveservice.entity.SenderInfo();
        dto.setId(BigInteger.valueOf(id));
        dto.setSenderName(name);

        dtoInfo.setId(BigInteger.valueOf(id));
        domenInfo.setId(id);
        dtoInfo.setPhoneNumber(number);
        domenInfo.setSenderPhoneNumber(number);
        dtoInfo.setEmail(email);
        domenInfo.setSenderEmail(email);
        dtoInfo.setFax(fax);
        domenInfo.setSenderFax(fax);

        dto.setSenderInfo(dtoInfo);
        domen.setInfo(domenInfo);

        by.epam.rafalovich.archiveservice.entity.Sender result = mapper.map(dto, by.epam.rafalovich.archiveservice.entity.Sender.class);

        assertEquals(domen, result);
    }

    @Test
    public void testSenderUnmarshalling() {

        long id = 11L;
        String name = "hotel_california";
        String fax = "12345";
        String email = "qwerty@gmail.com";
        String number = "+375336012781";


        by.epam.rafalovich.archiveservice.entity.Sender domen = new by.epam.rafalovich.archiveservice.entity.Sender();
        domen.setSenderId(id);
        domen.setSenderName(name);

        by.epam.rafalovich.archiveservice.entity.SenderInfo domenInfo = new by.epam.rafalovich.archiveservice.entity.SenderInfo();
        domenInfo.setId(id);
        domenInfo.setSenderPhoneNumber(number);
        domenInfo.setSenderEmail(email);
        domenInfo.setSenderFax(fax);
        domen.setInfo(domenInfo);

        by.epam.rafalovich.archiveservice.Sender result = mapper.map(domen, by.epam.rafalovich.archiveservice.Sender.class);

        assertPropertyLenientEquals("id", BigInteger.valueOf(id), result);
        assertPropertyLenientEquals("senderName", name, result);
        assertPropertyLenientEquals("senderInfo.email", email, result);
        assertPropertyLenientEquals("senderInfo.fax", fax, result);
        assertPropertyLenientEquals("senderInfo.phoneNumber", number, result);
        assertPropertyLenientEquals("senderInfo.id", id, result);
    }

    @Test
    public void testRecordMarshalling() throws DatatypeConfigurationException {

        long id = 11L;
        String name = "hotel_california";
        String fax = "12345";
        String email = "qwerty@gmail.com";
        String recipientNumber = "+375336012781";
        String number = "+375336012781";
        String dateTime = "2002-05-30T09:00:00";

        OperationType confirmation = OperationType.RESERVATION_CONFIRMATION;
        by.epam.rafalovich.archiveservice.entity.OperationType operationType = by.epam.rafalovich.archiveservice.entity.OperationType.RESERVATION_CONFIRMATION;
        ChannelType sms = ChannelType.SMS;
        CommunicationChannel channel = CommunicationChannel.SMS;

        int year = 2002;
        int month = 5;
        int day = 30;
        int hour = 9;
        int minute = 0;
        int second = 0;

        Record dto = new Record();
        dto.setId(BigInteger.valueOf(id));
        dto.setOperation(confirmation);

        Recipient recipient = new Recipient();
        recipient.setId(BigInteger.valueOf(id));
        recipient.setChannelType(sms);
        recipient.setRecipientContact(recipientNumber);

        dto.setRecipient(recipient);

        Sender sender = new Sender();
        sender.setSenderName(name);
        sender.setId(BigInteger.valueOf(id));

        SenderInfo info = new SenderInfo();
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
    public void testRecordUnmarshalling() throws DatatypeConfigurationException {

        long id = 11L;
        String name = "hotel_california";
        String fax = "12345";
        String email = "qwerty@gmail.com";
        String recipientNumber = "+375336012781";
        String number = "+375336012781";
        String dateTime = "2002-05-30T09:00:00";

        OperationType confirmation = OperationType.RESERVATION_CONFIRMATION;
        by.epam.rafalovich.archiveservice.entity.OperationType operationType = by.epam.rafalovich.archiveservice.entity.OperationType.RESERVATION_CONFIRMATION;
        ChannelType sms = ChannelType.SMS;
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

        by.epam.rafalovich.archiveservice.entity.Recipient recipient = new by.epam.rafalovich.archiveservice.entity.Recipient();
        recipient.setRecipientId(id);
        recipient.setChanel(channel);
        recipient.setRecipientContact(recipientNumber);

        record.setRecipient(recipient);

        by.epam.rafalovich.archiveservice.entity.Sender sender = new by.epam.rafalovich.archiveservice.entity.Sender();
        sender.setSenderId(id);
        sender.setSenderName(name);

        by.epam.rafalovich.archiveservice.entity.SenderInfo info = new by.epam.rafalovich.archiveservice.entity.SenderInfo();
        info.setId(id);
        info.setSenderFax(fax);
        info.setSenderEmail(email);
        info.setSenderPhoneNumber(number);

        sender.setInfo(info);
        record.setSender(sender);

        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTime);

        Record dto = mapper.map(record, Record.class);

        assertPropertyLenientEquals("dateTime", xcal, dto);
        assertPropertyLenientEquals("operation", confirmation, dto);
        assertPropertyLenientEquals("sender.senderName", name, dto);
        assertPropertyLenientEquals("sender.senderInfo.fax", fax, dto);
        assertPropertyLenientEquals("recipient.channelType", sms, dto);
        assertPropertyLenientEquals("recipient.recipientContact", recipientNumber, dto);
    }

    @Test
    public void testCancellationOperationTypeMarshalling() {

        OperationType cancellation = OperationType.RESERVATION_CANCELLATION;

        by.epam.rafalovich.archiveservice.entity.OperationType result =
                mapper.map(cancellation, by.epam.rafalovich.archiveservice.entity.OperationType.class);

        assertNotNull(result);
        assertEquals(result, by.epam.rafalovich.archiveservice.entity.OperationType.RESERVATION_CANCELLATION);
    }


    @Test
    public void testCancellationOOperationTypeUnmarshalling() {

        by.epam.rafalovich.archiveservice.entity.OperationType cancellation =
                by.epam.rafalovich.archiveservice.entity.OperationType.RESERVATION_CANCELLATION;

        OperationType result = mapper.map(cancellation, OperationType.class);

        assertNotNull(result);
        assertEquals(result, by.epam.rafalovich.archiveservice.OperationType.RESERVATION_CANCELLATION);
    }

    @Test
    public void testConfirmationOperationTypeMarshalling() {

        OperationType cancellation = OperationType.RESERVATION_CONFIRMATION;

        by.epam.rafalovich.archiveservice.entity.OperationType result =
                mapper.map(cancellation, by.epam.rafalovich.archiveservice.entity.OperationType.class);

        assertNotNull(result);
        assertEquals(result, by.epam.rafalovich.archiveservice.entity.OperationType.RESERVATION_CONFIRMATION);
    }


    @Test
    public void testConfirmationOOperationTypeUnmarshalling() {

        by.epam.rafalovich.archiveservice.entity.OperationType cancellation =
                by.epam.rafalovich.archiveservice.entity.OperationType.RESERVATION_CONFIRMATION;

        OperationType result = mapper.map(cancellation, OperationType.class);

        assertNotNull(result);
        assertEquals(result, by.epam.rafalovich.archiveservice.OperationType.RESERVATION_CONFIRMATION);
    }

    @Test
    public void testChangingOperationTypeMarshalling() {

        OperationType cancellation = OperationType.RESERVATION_CHANGING;

        by.epam.rafalovich.archiveservice.entity.OperationType result =
                mapper.map(cancellation, by.epam.rafalovich.archiveservice.entity.OperationType.class);

        assertNotNull(result);
        assertEquals(result, by.epam.rafalovich.archiveservice.entity.OperationType.RESERVATION_CHANGING);
    }


    @Test
    public void testChangingOOperationTypeUnmarshalling() {

        by.epam.rafalovich.archiveservice.entity.OperationType cancellation =
                by.epam.rafalovich.archiveservice.entity.OperationType.RESERVATION_CHANGING;

        OperationType result = mapper.map(cancellation, OperationType.class);

        assertNotNull(result);
        assertEquals(result, by.epam.rafalovich.archiveservice.OperationType.RESERVATION_CHANGING);
    }


}
