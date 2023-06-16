package org.example.src.services;

import org.example.src.db.ServicesDatabase;
import org.example.src.entity.Cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;

public class ServerServiceImpl extends UnicastRemoteObject implements ServerService {
    public ServerServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public Cliente searchRequest(String file) throws RemoteException {
        ServicesDatabase servicesDatabase = new ServicesDatabase();
        Connection connection = servicesDatabase.connect_to_db("napster_service", "postgres", "postgres");
        Cliente clientFiles = servicesDatabase.findClientFiles(connection, file);

        return clientFiles;
    }

    @Override
    public String joinRequest(Cliente cliente) throws RemoteException {
        ServicesDatabase servicesDatabase = new ServicesDatabase();
        Connection connection = servicesDatabase.connect_to_db("napster_service", "postgres", "postgres");
        String result = servicesDatabase.insertNewClient(connection, cliente);

        return result;
    }

    @Override
    public String updateRequest(String name) throws RemoteException {
        ServicesDatabase servicesDatabase = new ServicesDatabase();
        Connection connection = servicesDatabase.connect_to_db("napster_service", "postgres", "postgres");
        String result = servicesDatabase.updateNewRequestDownload(connection, name);

        return result;
    }
}
