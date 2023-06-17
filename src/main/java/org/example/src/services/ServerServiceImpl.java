package org.example.src.services;

import org.example.src.db.ServicesDatabase;
import org.example.src.entity.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;

public class ServerServiceImpl extends UnicastRemoteObject implements ServerService {
    public ServerServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public Client searchRequest(String file) throws RemoteException {
        ServicesDatabase servicesDatabase = new ServicesDatabase();
        Connection connection = servicesDatabase.connect_to_db("napster_service", "postgres", "postgres");
        Client clientFiles = servicesDatabase.findClientFiles(connection, file);

        return clientFiles;
    }

    @Override
    public String joinRequest(Client client) throws RemoteException {
        ServicesDatabase servicesDatabase = new ServicesDatabase();
        Connection connection = servicesDatabase.connect_to_db("napster_service", "postgres", "postgres");
        String result = servicesDatabase.insertNewClient(connection, client);

        System.out.println("Sou peer " + client.getIp() + ":" + client.getClient_port() + " com arquivos " + client.getFiles());
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
