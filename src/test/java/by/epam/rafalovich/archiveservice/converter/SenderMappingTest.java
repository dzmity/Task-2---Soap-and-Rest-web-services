package by.epam.rafalovich.archiveservice.converter;


import by.epam.rafalovich.archiveservice.SenderInfoType;
import by.epam.rafalovich.archiveservice.SenderType;
import by.epam.rafalovich.archiveservice.entity.Sender;
import by.epam.rafalovich.archiveservice.entity.SenderInfo;
import org.dozer.DozerBeanMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertPropertyLenientEquals;

/**
 * Created by Dzmitry_Rafalovich on 2/8/2017.
 */
public class SenderMappingTest {

    private static final String PATH = "mapping/dozer_mapping.xml";

    private static DozerBeanMapper mapper;

    @BeforeClass
    public static void before() throws Exception {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList(PATH));
    }

    @Test
    public void testSenderTypeMapping() {

        long id = 10L;
        String name = "hotel";
        String fax = "123";
        String email = "qwerty@gmail.com";
        String number = "+375336012781";

        SenderType dto = new SenderType();
        Sender domain = new Sender();
        domain.setSenderId(id);
        domain.setSenderName(name);

        SenderInfoType dtoInfo = new SenderInfoType();
        SenderInfo domainInfo = new SenderInfo();
        dto.setId(BigInteger.valueOf(id));
        dto.setSenderName(name);

        dtoInfo.setId(BigInteger.valueOf(id));
        domainInfo.setId(id);
        dtoInfo.setPhoneNumber(number);
        domainInfo.setSenderPhoneNumber(number);
        dtoInfo.setEmail(email);
        domainInfo.setSenderEmail(email);
        dtoInfo.setFax(fax);
        domainInfo.setSenderFax(fax);

        dto.setSenderInfo(dtoInfo);
        domain.setInfo(domainInfo);

        Sender result = mapper.map(dto, Sender.class);

        assertEquals(domain, result);
    }

    @Test
    public void testSenderMapping() {

        long id = 11L;
        String name = "hotel_california";
        String fax = "12345";
        String email = "qwerty@gmail.com";
        String number = "+375336012781";


        Sender domain = new Sender();
        domain.setSenderId(id);
        domain.setSenderName(name);

        SenderInfo domainInfo = new SenderInfo();
        domainInfo.setId(id);
        domainInfo.setSenderPhoneNumber(number);
        domainInfo.setSenderEmail(email);
        domainInfo.setSenderFax(fax);
        domain.setInfo(domainInfo);

        SenderType result = mapper.map(domain, SenderType.class);

        assertPropertyLenientEquals("id", BigInteger.valueOf(id), result);
        assertPropertyLenientEquals("senderName", name, result);
        assertPropertyLenientEquals("senderInfo.email", email, result);
        assertPropertyLenientEquals("senderInfo.fax", fax, result);
        assertPropertyLenientEquals("senderInfo.phoneNumber", number, result);
        assertPropertyLenientEquals("senderInfo.id", id, result);
    }
}
