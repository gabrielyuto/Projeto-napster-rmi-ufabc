package org.example.src.services;

import org.example.src.db.ServicesDatabase;
import org.example.src.client.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.util.List;

//    FUNCIONALIDADES DO SERVIDOR RMI
public class ServerServiceImpl extends UnicastRemoteObject implements ServerService {
    public ServerServiceImpl() throws RemoteException {
        super();
    }

    //    REQUISICAO JOIN
    @Override
    public String joinRequest(Client client) throws RemoteException {
        ServicesDatabase servicesDatabase = new ServicesDatabase();
        Connection connection = servicesDatabase.connect_to_db("napster_service", "postgres", "postgres");
        String result = servicesDatabase.insertNewClient(connection, client);

        System.out.println("Sou peer " + client.getIp() + ":" + client.getClient_port() + " com arquivos " + client.getFiles());
        return result;
    }

    //    REQUISICAO SEARCH
    @Override
    public List<Client> searchRequest(Client client) throws RemoteException {
        ServicesDatabase servicesDatabase = new ServicesDatabase();
        Connection connection = servicesDatabase.connect_to_db("napster_service", "postgres", "postgres");
        List<Client> clientFiles = servicesDatabase.findClientFiles(connection, client);

        System.out.println("Peer " + client.getIp() + ":" + client.getClient_port() + " solicitou arquivo " + client.getFile_request());
        return clientFiles;
    }

    //    REQUISICAO UPDATE
    @Override
    public String updateRequest(Client client) throws RemoteException {
        ServicesDatabase servicesDatabase = new ServicesDatabase();
        Connection connection = servicesDatabase.connect_to_db("napster_service", "postgres", "postgres");
        String result = servicesDatabase.updateNewRequestDownload(connection, client);

        return result;
    }
}
