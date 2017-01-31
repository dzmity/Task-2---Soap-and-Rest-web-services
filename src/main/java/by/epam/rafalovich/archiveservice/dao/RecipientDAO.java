package by.epam.rafalovich.archiveservice.dao;


import by.epam.rafalovich.archiveservice.entity.Recipient;
import by.epam.rafalovich.archiveservice.exception.DAOException;

/**
 * Created by Dzmitry_Rafalovich on 1/21/2017.
 */
public interface RecipientDAO extends GenericDAO<Recipient> {

    Recipient findRecipientByContact(String contact) throws DAOException;
}
