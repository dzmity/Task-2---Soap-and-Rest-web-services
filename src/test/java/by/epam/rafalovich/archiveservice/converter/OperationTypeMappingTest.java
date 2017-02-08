package by.epam.rafalovich.archiveservice.converter;

import by.epam.rafalovich.archiveservice.OperationType;
import org.dozer.DozerBeanMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Dzmitry_Rafalovich on 2/8/2017.
 */
public class OperationTypeMappingTest {

    private static final String PATH = "mapping/dozer_mapping.xml";

    private static DozerBeanMapper mapper;

    @BeforeClass
    public static void before() throws Exception {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList(PATH));
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
