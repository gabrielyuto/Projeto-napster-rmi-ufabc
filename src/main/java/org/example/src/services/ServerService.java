package org.example.src.services;

import org.example.src.client.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerService extends Remote {
    public List<Client> searchRequest(Client client) throws RemoteException;

    public String joinRequest(Client client) throws RemoteException;

    public String updateRequest(Client client) throws RemoteException;
}
