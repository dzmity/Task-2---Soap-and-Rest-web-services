package by.epam.rafalovich.archiveservice.converter;

import org.junit.Test;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created by Dzmitry_Rafalovich on 2/15/2017.
 */
public class DateTimeConverterTest {

    private static final Class<?> CALENDAR_CLASS_IMPL = XMLGregorianCalendar.class;
    private static final Class<?> DATE_TIME_CLASS = LocalDateTime.class;
    private static final DateTimeConverter dateTimeConverter = new DateTimeConverter();

    @Test
    public void testNullSourceConverterLocalDateTimeToXMLGregorianCalendar() throws DatatypeConfigurationException{

        LocalDateTime source = null;
        String dateTime = "2015-05-30T09:00:00";
        XMLGregorianCalendar destination = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTime);
        destination =(XMLGregorianCalendar) dateTimeConverter.convert(destination, source, CALENDAR_CLASS_IMPL, DATE_TIME_CLASS);

        assertEquals(null, destination);
    }

    @Test
    public void testConverterLocalDateTimeToXMLGregorianCalendar() throws DatatypeConfigurationException {

        LocalDateTime source = LocalDateTime.now();
        String[] sourceDateTime = source.toString().split("\\.");
        String dateTime = "2010-05-30T09:00:00";

        XMLGregorianCalendar destination = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTime);
        XMLGregorianCalendar expected = DatatypeFactory.newInstance().newXMLGregorianCalendar(sourceDateTime[0]);
        destination =(XMLGregorianCalendar) dateTimeConverter.convert(destination, source, CALENDAR_CLASS_IMPL, DATE_TIME_CLASS);

       assertEquals(expected, destination);
    }

    @Test
    public void testConverterXMLGregorianCalendarToLocalDateTime() throws DatatypeConfigurationException {

        int year = 2002;
        int month = 5;
        int day = 30;
        int hour = 9;
        int minute = 0;
        int second = 0;
        String dateTime = "2002-05-30T09:00:00";

        XMLGregorianCalendar source = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTime);
        LocalDateTime expected = LocalDateTime.of(year, month, day, hour, minute, second);
        LocalDateTime destination = LocalDateTime.now();
        destination =(LocalDateTime) dateTimeConverter.convert(destination, source, DATE_TIME_CLASS, CALENDAR_CLASS_IMPL);

        assertEquals(expected, destination);
    }

    @Test
    public void testNotCastedSourceAndSourceClass() throws DatatypeConfigurationException {

        LocalDate notAssignableSource = LocalDate.now();
        String dateTime = "2010-05-30T09:00:00";
        XMLGregorianCalendar destination = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTime);
        destination =(XMLGregorianCalendar) dateTimeConverter.convert(destination, notAssignableSource, CALENDAR_CLASS_IMPL, DATE_TIME_CLASS);

        assertEquals(null, destination);
    }

    @Test
    public void testNotAssignableClasses() throws DatatypeConfigurationException {

        LocalDate source = LocalDate.now();
        String dateTime = "2010-05-30T09:00:00";
        Class<String> nonAssignableClass = String.class;
        XMLGregorianCalendar destination = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTime);
        destination =(XMLGregorianCalendar) dateTimeConverter.convert(destination, source, nonAssignableClass, DATE_TIME_CLASS);

        assertEquals(null, destination);
    }
}