package org.example.src.services;

import org.example.src.entity.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerService extends Remote {
    public Client searchRequest(String file) throws RemoteException;

    public String joinRequest(Client client) throws RemoteException;

    public String updateRequest(String name) throws RemoteException;
}
