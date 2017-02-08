package by.epam.rafalovich.archiveservice.converter;

import by.epam.rafalovich.archiveservice.SenderInfo;
import org.dozer.DozerBeanMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Created by Dzmitry_Rafalovich on 2/8/2017.
 */
public class SenderInfoMappingTest {

    private static final String PATH = "mapping/dozer_mapping.xml";

    private static DozerBeanMapper mapper;

    @BeforeClass
    public static void before() throws Exception {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList(PATH));
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
}
