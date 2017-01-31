package by.epam.rafalovich.archiveservice.dao;


import by.epam.rafalovich.archiveservice.entity.Sender;
import by.epam.rafalovich.archiveservice.exception.DAOException;

/**
 * Created by Dzmitry_Rafalovich on 1/21/2017.
 */
public interface SenderDAO extends GenericDAO<Sender> {

    Sender findSenderBySenderName(String name) throws DAOException;

    Sender findSenderByPhoneNumber(String number) throws DAOException;

    Sender findSenderByFax(String fax) throws DAOException;

    Sender findSenderByEmail(String email) throws DAOException;

}
