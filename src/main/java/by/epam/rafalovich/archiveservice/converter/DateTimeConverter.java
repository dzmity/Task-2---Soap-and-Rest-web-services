package by.epam.rafalovich.archiveservice.converter;

import org.dozer.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;

/**
 * Created by Dzmitry_Rafalovich on 2/1/2017.
 */
public class DateTimeConverter implements CustomConverter {

    private static final Logger LOG = LoggerFactory.getLogger(DateTimeConverter.class);

    @Override
    public Object convert(Object destination, Object source, Class destinationClass, Class sourceClass) {

        if (null == source) {

            LOG.info("Source is null. Return null.");
            return null;
        }

        try {

            if (XMLGregorianCalendar.class.isAssignableFrom(sourceClass)&& LocalDateTime.class.isAssignableFrom(destinationClass)) {

                LOG.info("Mapping XMLGregorianCalendar to LocalDateTime.");

                XMLGregorianCalendar calendar = (XMLGregorianCalendar) source;
                LocalDateTime dateTime = LocalDateTime.of(calendar.getYear(), calendar.getMonth(), calendar.getDay(),
                        calendar.getHour(), calendar.getMinute(), calendar.getSecond());
                return dateTime;

            }else if (XMLGregorianCalendar.class.isAssignableFrom(destinationClass) && LocalDateTime.class.isAssignableFrom(sourceClass)) {

                LOG.info("Mapping LocalDateTime to XMLGregorianCalendar.");

                LocalDateTime localDateTime = (LocalDateTime) source;
                XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
                xcal.setYear(localDateTime.getYear());
                xcal.setMonth(localDateTime.getMonthValue());
                xcal.setDay(localDateTime.getDayOfMonth());
                xcal.setHour(localDateTime.getHour());
                xcal.setMinute(localDateTime.getMinute());
                xcal.setSecond(localDateTime.getSecond());
                return xcal;
            }

        } catch (DatatypeConfigurationException e) {
            LOG.error("Wrong dateTime data for mapping LocalDateTime to XMLGregorianCalendar from request. Return null.");
            return null;
        } catch (ClassCastException e) {
            LOG.error("Impossible to convert source to sourceClass. Return null.");
            return null;
        }

        LOG.error("Wrong combination of sourceClass and destinationClass. Return null.");
        return null;
    }
}
