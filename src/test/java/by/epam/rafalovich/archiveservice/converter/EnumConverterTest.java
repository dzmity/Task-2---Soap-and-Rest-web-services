package by.epam.rafalovich.archiveservice.converter;

import by.epam.rafalovich.archiveservice.OperationType;
import by.epam.rafalovich.archiveservice.entity.Operation;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnumConverterTest {

    private static final EnumConverter converter = new EnumConverter();
    private static final Class OPERATION_CLASS = Operation.class;
    private static final Class OPERATION_TYPE_CLASS = OperationType.class;

    @Test
    public void testNullSourceConversion() {

        Operation source = null;
        OperationType result = OperationType.RESERVATION_CANCELLATION;
        result = (OperationType) converter.convert(result, source, OPERATION_TYPE_CLASS, OPERATION_CLASS);

        assertNull(result);
    }

    @Test
    public void testNotAssignableClasses() {

        Operation source = null;
        OperationType result = OperationType.RESERVATION_CANCELLATION;
        Class notAssignableClass = String.class;
        result = (OperationType) converter.convert(result, source, notAssignableClass, OPERATION_CLASS);

        assertNull(result);
    }


    @Test
    public void testEnumConversion() {

        Operation source = Operation.RESERVATION_CANCELLATION;

        OperationType result = OperationType.RESERVATION_CHANGING;

        result = (OperationType) converter.convert(result, source, OPERATION_TYPE_CLASS, OPERATION_CLASS);

        assertNotNull(result);
        assertEquals(result, OperationType.RESERVATION_CANCELLATION);
    }
}