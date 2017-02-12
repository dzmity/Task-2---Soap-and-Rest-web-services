package by.epam.rafalovich.archiveservice.converter;

import by.epam.rafalovich.archiveservice.OperationType;
import by.epam.rafalovich.archiveservice.RecordCriteriaType;
import by.epam.rafalovich.archiveservice.entity.Operation;
import by.epam.rafalovich.archiveservice.entity.RecordCriteria;
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
 * Created by admin on 12.02.2017.
 */
public class RecordCriteriaMappingTest {

    private static final String PATH = "mapping/dozer_mapping.xml";

    private static DozerBeanMapper mapper;

    @BeforeClass
    public static void before() throws Exception {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList(PATH));
    }

    private RecordCriteria initCriteria(Long id, Operation operation, Long recipientId, int startYear,
                                        int endYear, int month, int day, int hour, int minute, int second) {

        LocalDateTime startLocalDateTime = LocalDateTime.of(startYear, month, day, hour, minute, second);
        LocalDateTime endLocalDateTime = LocalDateTime.of(endYear, month, day, hour, minute, second);
        RecordCriteria recordCriteria = new RecordCriteria();
        recordCriteria.setSenderId(id);
        recordCriteria.setOperationType(operation);
        recordCriteria.setStartDateTime(startLocalDateTime);
        recordCriteria.setEndDateTime(endLocalDateTime);
        return recordCriteria;
    }

    private RecordCriteriaType initCriteriaType(long id, OperationType operationType, String recipientContact,
                                                String startDateTime, String endDateTime) throws DatatypeConfigurationException {

        BigInteger senderId = BigInteger.valueOf(id);
        XMLGregorianCalendar start = DatatypeFactory.newInstance().newXMLGregorianCalendar(startDateTime);
        XMLGregorianCalendar end = DatatypeFactory.newInstance().newXMLGregorianCalendar(endDateTime);
        RecordCriteriaType criteriaType = new RecordCriteriaType();
        criteriaType.setSenderId(senderId);
        criteriaType.setRecipientContact(recipientContact);
        criteriaType.setOperationType(operationType);
        criteriaType.setStartDateTime(start);
        criteriaType.setEndDateTime(end);

        return criteriaType;
    }

    @Test
    public void testRecordCriteriaTypeMapping() throws DatatypeConfigurationException {

        long id = 1L;
        String recipientContact = "contact";
        String startDateTime = "2002-05-30T09:00:00";
        String endDateTime = "2015-05-30T09:00:00";

        OperationType operationType = OperationType.RESERVATION_CONFIRMATION;
        Operation operation = Operation.RESERVATION_CONFIRMATION;

        int startYear = 2002;
        int endYear = 2015;
        int month = 5;
        int day = 30;
        int hour = 9;
        int minute = 0;
        int second = 0;

        RecordCriteriaType criteriaType = initCriteriaType(id, operationType, recipientContact, startDateTime, endDateTime);
        RecordCriteria recordCriteria = initCriteria(id, operation, null, startYear, endYear, month, day, hour, minute, second);

        RecordCriteria result = mapper.map(criteriaType, RecordCriteria.class);

        assertEquals(result, recordCriteria);
    }

    @Test
    public void testRecordCriteriaMapping() throws DatatypeConfigurationException {

        long id = 1L;
        String recipientContact = "contact";
        String startDateTime = "2002-05-30T09:00:00";
        String endDateTime = "2015-05-30T09:00:00";

        OperationType operationType = OperationType.RESERVATION_CONFIRMATION;
        Operation operation = Operation.RESERVATION_CONFIRMATION;

        int startYear = 2002;
        int endYear = 2015;
        int month = 5;
        int day = 30;
        int hour = 9;
        int minute = 0;
        int second = 0;

        RecordCriteriaType criteriaType = initCriteriaType(id, operationType, recipientContact, startDateTime, endDateTime);
        RecordCriteria recordCriteria = initCriteria(id, operation, null, startYear, endYear, month, day, hour, minute, second);

        RecordCriteriaType result = mapper.map(recordCriteria, RecordCriteriaType.class);

        assertPropertyLenientEquals("senderId", criteriaType.getSenderId(), result);
        assertPropertyLenientEquals("operationType", criteriaType.getOperationType(), result);
        assertPropertyLenientEquals("startDateTime", criteriaType.getStartDateTime(), result);
        assertPropertyLenientEquals("endDateTime", criteriaType.getEndDateTime(), result);
    }
}
