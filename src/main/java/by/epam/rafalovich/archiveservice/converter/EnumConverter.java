package by.epam.rafalovich.archiveservice.converter;

import org.dozer.CustomConverter;

/**
 * Created by Dzmitry_Rafalovich on 2/1/2017.
 */
public class EnumConverter implements CustomConverter {

    @Override
    public Object convert(Object destination, Object source, Class destinationClass, Class sourceClass) {

        if (null == source) {
            return null;
        }

        if (Enum.class.isAssignableFrom(sourceClass)
            && Enum.class.isAssignableFrom(destinationClass)) {
                return Enum.valueOf((Class<Enum>) destinationClass,
                        source.toString());
            }
        return null;
    }
}
