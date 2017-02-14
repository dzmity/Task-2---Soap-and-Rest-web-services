package by.epam.rafalovich.archiveservice.converter;

import org.dozer.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Dzmitry_Rafalovich on 2/1/2017.
 */
public class EnumConverter implements CustomConverter {

    private static final Logger LOG = LoggerFactory.getLogger(EnumConverter.class);

    @Override
    public Object convert(Object destination, Object source, Class destinationClass, Class sourceClass) {

        LOG.info("Mapping Enum classes to EnumType class.");

        if (null == source) {
            return null;
        }

        if (Enum.class.isAssignableFrom(sourceClass) && Enum.class.isAssignableFrom(destinationClass)) {

                return Enum.valueOf((Class<Enum>) destinationClass, source.toString());
        }

        LOG.error("Enum classes are not assignable.");
        return null;
    }
}
