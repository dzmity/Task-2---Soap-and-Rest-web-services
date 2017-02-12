package by.epam.rafalovich.archiveservice.converter;


import by.epam.rafalovich.archiveservice.SenderInfoType;
import by.epam.rafalovich.archiveservice.entity.SenderInfo;
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
    public void testSenderInfoTypeMapping() {

        long id = 12L;
        String fax = "123";
        String email = "qwerty@gmail.com";
        String number = "+375336012781";

        SenderInfoType dto = new SenderInfoType();
        dto.setId(BigInteger.valueOf(id));
        dto.setFax(fax);
        dto.setEmail(email);
        dto.setPhoneNumber(number);

        SenderInfo domain = mapper.map(dto, SenderInfo.class);

        assertTrue(domain.getId() == id);
        assertEquals(domain.getSenderFax(), dto.getFax());
        assertEquals(domain.getSenderEmail(), dto.getEmail());
    }

    @Test
    public void testSenderInfoeMapping() {

        long id = 12L;
        String fax = "123";
        String email = "qwerty@gmail.com";
        String number = "+375336012781";

        SenderInfo domain = new SenderInfo();
        domain.setId(id);
        domain.setSenderEmail(email);
        domain.setSenderFax(fax);
        domain.setSenderPhoneNumber(number);

        SenderInfoType dto = mapper.map(domain, SenderInfoType.class);
        assertSame(dto.getId(), BigInteger.valueOf(id));
        assertEquals(domain.getSenderFax(), dto.getFax());
        assertEquals(domain.getSenderEmail(), dto.getEmail());
    }
}
